public class AdvisingSystemPhase2 implements IAdvisingSystemPhase2 {
    private Set<IAdvisor> advisors;
    private Set<IStudent> students;
    private Set<ILocation> locations;
    private Set<IMeeting> meetings;
    private Set<IWorkshop> workshops;
    private Set<IEvent> events;
    private int nextEventId;
    private Map<Integer, IAdvisor> advisorMap;
    private Map<Integer, IStudent> studentMap;
    private Map<Integer, ILocation> locationMap;
    private Map<Integer, IEvent> eventMap;

    public AdvisingSystemPhase2() {
        advisors = new BSTSet<>();
        students = new BSTSet<>();
        locations = new BSTSet<>();
        meetings = new BSTSet<>();
        workshops = new BSTSet<>();
        events = new BSTSet<>();
        nextEventId = 1;
        advisorMap = new BSTMap<>();
        studentMap = new BSTMap<>();
        locationMap = new BSTMap<>();
        eventMap = new BSTMap<>();
    }

    public Set<IAdvisor> getAdvisors() { return advisors; }
    public Set<IStudent> getStudents() { return students; }
    public Set<ILocation> getLocations() { return locations; }
    public Set<IMeeting> getMeetings() { return meetings; }
    public Set<IWorkshop> getWorkshops() { return workshops; }
    public Set<IEvent> getEvents() { return events; }

    public boolean addAdvisor(IAdvisor advisor) {
        if (advisor == null) return false;
        if (advisorMap.get(advisor.getId()) != null) return false;
        advisors.insert(advisor);
        advisorMap.insert(advisor.getId(), advisor);
        return true;
    }

    public IAdvisor searchAdvisorById(int advisorId) {
        return advisorMap.get(advisorId);
    }

    public boolean addStudent(IStudent student) {
        if (student == null) return false;
        if (studentMap.get(student.getId()) != null) return false;
        students.insert(student);
        studentMap.insert(student.getId(), student);
        return true;
    }

    public IStudent searchStudentById(int studentId) {
        return studentMap.get(studentId);
    }

    public boolean deleteStudent(int studentId) {
        IStudent student = searchStudentById(studentId);
        if (student == null) return false;

        List<IEvent> eventList = events.getKeys();
        if (!eventList.empty()) {
            eventList.findFirst();
            while (true) {
                IEvent event = (IEvent) eventList.retrieve();
                if (event != null && event.getParticipantIds().contains(studentId)) {
                    if (event instanceof IMeeting) {
                        cancelMeeting(event.getId());
                    } else if (event instanceof IWorkshop) {
                        try {
                            removeStudentFromWorkshop(event.getId(), studentId);
                        } catch (SchedulingException e) {}
                    }
                }
                if (eventList.last()) break;
                eventList.findNext();
            }
        }

        students.remove(student);
        studentMap.remove(studentId);
        return true;
    }

    public boolean addLocation(ILocation location) {
        if (location == null) return false;
        if (locationMap.get(location.getId()) != null) return false;
        locations.insert(location);
        locationMap.insert(location.getId(), location);
        return true;
    }

    public ILocation searchLocationById(int locationId) {
        return locationMap.get(locationId);
    }

    public int scheduleMeeting(ITimeSlot timeSlot, int advisorId, int studentId)
            throws SchedulingException {
        IAdvisor advisor = searchAdvisorById(advisorId);
        if (advisor == null) throw new SchedulingException(ScheduleFailureReason.ADVISOR_NOT_FOUND);

        IStudent student = searchStudentById(studentId);
        if (student == null) throw new SchedulingException(ScheduleFailureReason.STUDENT_NOT_FOUND);

        if (advisor.getSchedule().conflicts(timeSlot))
            throw new SchedulingException(ScheduleFailureReason.CONFLICT_ADVISOR);

        if (student.getSchedule().conflicts(timeSlot))
            throw new SchedulingException(ScheduleFailureReason.CONFLICT_STUDENT);

        int meetingId = nextEventId++;
        ILocation office = advisor.getOffice();
        IMeeting meeting = new Meeting(meetingId, timeSlot, office, advisorId, studentId);

        advisor.getSchedule().add(meetingId, timeSlot);
        student.getSchedule().add(meetingId, timeSlot);
        meetings.insert(meeting);
        events.insert(meeting);
        eventMap.insert(meetingId, meeting);

        return meetingId;
    }

    public int scheduleWorkshop(String title, ITimeSlot timeSlot, int locationId,
                                int[] advisorIds, int[] studentIds)
            throws SchedulingException {
        ILocation location = searchLocationById(locationId);
        if (location == null) 
            throw new SchedulingException(ScheduleFailureReason.LOCATION_NOT_FOUND);

        if (!location.isReservable() && !location.isOnline()) {
            throw new SchedulingException(ScheduleFailureReason.LOCATION_NOT_RESERVABLE);
        }

        if (location.isReservable()) {
            if (location.getSchedule().conflicts(timeSlot))
                throw new SchedulingException(ScheduleFailureReason.CONFLICT_LOCATION);
            
            if (location.getCapacity() > 0) {
                int totalParticipants = advisorIds.length + studentIds.length;
                if (totalParticipants > location.getCapacity())
                    throw new SchedulingException(ScheduleFailureReason.CAPACITY_EXCEEDED);
            }
        }

        for (int advisorId : advisorIds) {
            if (searchAdvisorById(advisorId) == null)
                throw new SchedulingException(ScheduleFailureReason.ADVISOR_NOT_FOUND);
            if (searchAdvisorById(advisorId).getSchedule().conflicts(timeSlot))
                throw new SchedulingException(ScheduleFailureReason.CONFLICT_ADVISOR);
        }

        for (int studentId : studentIds) {
            if (searchStudentById(studentId) == null)
                throw new SchedulingException(ScheduleFailureReason.STUDENT_NOT_FOUND);
            if (searchStudentById(studentId).getSchedule().conflicts(timeSlot))
                throw new SchedulingException(ScheduleFailureReason.CONFLICT_STUDENT);
        }

        int workshopId = nextEventId++;
        Set<Integer> advisorSet = new BSTSet<>();
        Set<Integer> studentSet = new BSTSet<>();

        for (int advisorId : advisorIds) advisorSet.insert(advisorId);
        for (int studentId : studentIds) studentSet.insert(studentId);

        IWorkshop workshop = new Workshop(workshopId, title, timeSlot, location, advisorSet, studentSet);

        for (int advisorId : advisorIds) {
            searchAdvisorById(advisorId).getSchedule().add(workshopId, timeSlot);
        }

        for (int studentId : studentIds) {
            searchStudentById(studentId).getSchedule().add(workshopId, timeSlot);
        }

        if (location.isReservable()) {
            location.getSchedule().add(workshopId, timeSlot);
        }

        workshops.insert(workshop);
        events.insert(workshop);
        eventMap.insert(workshopId, workshop);

        return workshopId;
    }

    public boolean cancelMeeting(int meetingId) {
        IEvent event = eventMap.get(meetingId);
        if (event == null || !(event instanceof IMeeting)) return false;

        IMeeting meeting = (IMeeting) event;
        IAdvisor advisor = searchAdvisorById(meeting.getAdvisorId());
        IStudent student = searchStudentById(meeting.getStudentId());

        if (advisor != null) advisor.getSchedule().remove(meetingId);
        if (student != null) student.getSchedule().remove(meetingId);

        meetings.remove(meeting);
        events.remove(meeting);
        eventMap.remove(meetingId);
        return true;
    }

    public boolean cancelWorkshop(int workshopId) {
        IEvent event = eventMap.get(workshopId);
        if (event == null || !(event instanceof IWorkshop)) return false;

        IWorkshop workshop = (IWorkshop) event;

        List<Integer> advisorList = workshop.getAdvisorIds().getKeys();
        if (!advisorList.empty()) {
            advisorList.findFirst();
            while (true) {
                int advisorId = advisorList.retrieve();
                IAdvisor advisor = searchAdvisorById(advisorId);
                if (advisor != null) advisor.getSchedule().remove(workshopId);
                if (advisorList.last()) break;
                advisorList.findNext();
            }
        }

        List<Integer> studentList = workshop.getStudentIds().getKeys();
        if (!studentList.empty()) {
            studentList.findFirst();
            while (true) {
                int studentId = studentList.retrieve();
                IStudent student = searchStudentById(studentId);
                if (student != null) student.getSchedule().remove(workshopId);
                if (studentList.last()) break;
                studentList.findNext();
            }
        }

        ILocation location = workshop.getLocation();
        if (location != null && location.isReservable()) {
            location.getSchedule().remove(workshopId);
        }

        workshops.remove(workshop);
        events.remove(workshop);
        eventMap.remove(workshopId);
        return true;
    }

    public void addStudentToWorkshop(int workshopId, int studentId)
            throws SchedulingException {
        IEvent event = eventMap.get(workshopId);
        if (event == null || !(event instanceof IWorkshop))
            throw new SchedulingException(ScheduleFailureReason.EVENT_NOT_FOUND);

        IStudent student = searchStudentById(studentId);
        if (student == null) 
            throw new SchedulingException(ScheduleFailureReason.STUDENT_NOT_FOUND);

        IWorkshop workshop = (IWorkshop) event;
        
        if (workshop.getStudentIds().contains(studentId)) {
            return;
        }

        if (student.getSchedule().conflicts(workshop.getTimeSlot()))
            throw new SchedulingException(ScheduleFailureReason.CONFLICT_STUDENT);

        if (workshop.getLocation().isReservable() && workshop.getLocation().getCapacity() > 0) {
            int totalParticipants = workshop.getAdvisorIds().size() + workshop.getStudentIds().size();
            if (totalParticipants >= workshop.getLocation().getCapacity())
                throw new SchedulingException(ScheduleFailureReason.CAPACITY_EXCEEDED);
        }

        workshop.getStudentIds().insert(studentId);
        workshop.getParticipantIds().insert(studentId);
        student.getSchedule().add(workshopId, workshop.getTimeSlot());
    }

    public void removeStudentFromWorkshop(int workshopId, int studentId)
            throws SchedulingException {
        IEvent event = eventMap.get(workshopId);
        if (event == null || !(event instanceof IWorkshop))
            throw new SchedulingException(ScheduleFailureReason.EVENT_NOT_FOUND);

        IStudent student = searchStudentById(studentId);
        if (student == null) 
            throw new SchedulingException(ScheduleFailureReason.STUDENT_NOT_FOUND);

        IWorkshop workshop = (IWorkshop) event;
        if (!workshop.getStudentIds().contains(studentId))
            throw new SchedulingException(ScheduleFailureReason.STUDENT_NOT_FOUND);

        workshop.getStudentIds().remove(studentId);
        workshop.getParticipantIds().remove(studentId);
        student.getSchedule().remove(workshopId);

        if (workshop.getStudentIds().empty() && workshop.getAdvisorIds().empty()) {
            cancelWorkshop(workshopId);
        }
    }

    public void addAdvisorToWorkshop(int workshopId, int advisorId)
            throws SchedulingException {
        IEvent event = eventMap.get(workshopId);
        if (event == null || !(event instanceof IWorkshop))
            throw new SchedulingException(ScheduleFailureReason.EVENT_NOT_FOUND);

        IAdvisor advisor = searchAdvisorById(advisorId);
        if (advisor == null) 
            throw new SchedulingException(ScheduleFailureReason.ADVISOR_NOT_FOUND);

        IWorkshop workshop = (IWorkshop) event;
        
        if (workshop.getAdvisorIds().contains(advisorId)) {
            return;
        }

        if (advisor.getSchedule().conflicts(workshop.getTimeSlot()))
            throw new SchedulingException(ScheduleFailureReason.CONFLICT_ADVISOR);

        if (workshop.getLocation().isReservable() && workshop.getLocation().getCapacity() > 0) {
            int totalParticipants = workshop.getAdvisorIds().size() + workshop.getStudentIds().size();
            if (totalParticipants >= workshop.getLocation().getCapacity())
                throw new SchedulingException(ScheduleFailureReason.CAPACITY_EXCEEDED);
        }

        workshop.getAdvisorIds().insert(advisorId);
        workshop.getParticipantIds().insert(advisorId);
        advisor.getSchedule().add(workshopId, workshop.getTimeSlot());
    }

    public void removeAdvisorFromWorkshop(int workshopId, int advisorId)
            throws SchedulingException {
        IEvent event = eventMap.get(workshopId);
        if (event == null || !(event instanceof IWorkshop))
            throw new SchedulingException(ScheduleFailureReason.EVENT_NOT_FOUND);

        IAdvisor advisor = searchAdvisorById(advisorId);
        if (advisor == null) 
            throw new SchedulingException(ScheduleFailureReason.ADVISOR_NOT_FOUND);

        IWorkshop workshop = (IWorkshop) event;
        if (!workshop.getAdvisorIds().contains(advisorId))
            throw new SchedulingException(ScheduleFailureReason.ADVISOR_NOT_FOUND);

        workshop.getAdvisorIds().remove(advisorId);
        workshop.getParticipantIds().remove(advisorId);
        advisor.getSchedule().remove(workshopId);

        if (workshop.getStudentIds().empty() && workshop.getAdvisorIds().empty()) {
            cancelWorkshop(workshopId);
        }
    }
}