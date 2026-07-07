import java.io.File;
import java.util.Scanner;

public class AdvisingSystem implements IAdvisingSystem {
    private StudentList students;
    private EventList events;

    public AdvisingSystem() {
        students = new StudentList();
        events = new EventList();
    }

    public boolean loadStudentsFromCSV(String studentsFilePath) {
        try {
            Scanner scanner = new Scanner(new File(studentsFilePath));
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", -1);
                if (data.length >= 5) {
                    int id = Integer.parseInt(data[0].trim());
                    int year = Integer.parseInt(data[4].trim());
                    String notes = data.length >= 6 ? data[5].trim() : "";
                    addStudent(new Student(id, data[1].trim(), data[2].trim(), data[3].trim(), year, notes));
                }
            }
            scanner.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addStudent(IStudent student) {
        return students.add(student);
    }

    public IStudent searchStudentById(int studentId) {
        return students.findById(studentId);
    }

    public IStudent searchStudentByEmail(String email) {
        return students.findByEmail(email);
    }

    public void printStudentsByMajor(String major) {
        LinkedList<Student> list = students.findByMajor(major);
        printStudentList(list);
    }

    public void printStudentsByYearLevel(int yearLevel) {
        LinkedList<Student> list = students.findByYearLevel(yearLevel);
        printStudentList(list);
    }

    public void printStudentsByName(String fullName) {
        LinkedList<Student> list = students.findByName(fullName);
        printStudentList(list);
    }

    public void printStudentsByPartialName(String partialName) {
        LinkedList<Student> list = students.findByNameContains(partialName);
        printStudentList(list);
    }

    public void printAllStudents() {
        LinkedList<Student> list = students.getAll();
        printStudentList(list);
    }

    private void printStudentList(LinkedList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        list.findFirst();
        while (true) {
            System.out.println(list.retrieve().toString());
            if (list.last()) break;
            list.findNext();
        }
    }

    public boolean deleteStudent(int studentId) {
        IStudent student = students.findById(studentId);
        if (student == null) return false;

        LinkedList<IEvent> allEvents = events.getAllAlphabetically();
        if (!allEvents.isEmpty()) {
            allEvents.findFirst();
            while (true) {
                IEvent event = allEvents.retrieve();
                if (event.hasStudent(studentId)) {
                    if (event instanceof IMeeting) {
                        events.removeEventById(event.getEventId());
                    } else if (event instanceof IWorkshop) {
                        IWorkshop w = (IWorkshop) event;
                        w.removeParticipantById(studentId);
                        if (w.isEmpty()) {
                            events.removeEventById(event.getEventId());
                        }
                    }
                }
                if (allEvents.last()) break;
                allEvents.findNext();
            }
        }
        return students.deleteById(studentId);
    }

    private boolean isConflict(IDateTime s1, IDateTime e1, IDateTime s2, IDateTime e2) {
        return s1.compareTo(e2) < 0 && s2.compareTo(e1) < 0;
    }

    private boolean hasConflict(int studentId, IDateTime start, IDateTime end) {
        IStudent student = students.findById(studentId);
        if (student == null || student.getSchedule().isEmpty()) return false;
        
        student.getSchedule().findFirst();
        while (true) {
            IEvent event = student.getSchedule().retrieve();
            if (isConflict(start, end, event.getStartDateTime(), event.getEndDateTime())) {
                return true;
            }
            if (student.getSchedule().last()) break;
            student.getSchedule().findNext();
        }
        return false;
    }

    public boolean scheduleMeeting(String title, IDateTime startDateTime, IDateTime endDateTime, String location, int studentId) {
        IStudent student = students.findById(studentId);
        if (student == null) return false;
        if (hasConflict(studentId, startDateTime, endDateTime)) return false;

        Meeting m = new Meeting(events.size() + 1, title, startDateTime, endDateTime, location, student);
        if (events.addEvent(m)) {
            student.getSchedule().add(m); 
            return true;
        }
        return false;
    }

    public boolean scheduleWorkshop(String title, IDateTime startDateTime, IDateTime endDateTime, String location, int[] studentIds) {
        for (int id : studentIds) {
            if (students.findById(id) == null) return false;
            if (hasConflict(id, startDateTime, endDateTime)) return false;
        }

        Workshop w = new Workshop(events.size() + 1, title, startDateTime, endDateTime, location);
        for (int id : studentIds) {
            IStudent s = students.findById(id);
            w.addParticipant(s);
            s.getSchedule().add(w); 
        }
        return events.addEvent(w);
    }

    public void printEventDetailsByTitle(String title) {
        LinkedList<IEvent> list = events.findByTitle(title);
        printEventList(list);
    }

    public void printEventDetailsByStudentName(String studentName) {
        LinkedList<IEvent> list = events.findByStudentName(studentName);
        printEventList(list);
    }

    public void printWorkshopStudents(String workshopTitle) {
        LinkedList<IEvent> list = events.findByTitle(workshopTitle);
        if (list.isEmpty()) {
            System.out.println("Workshop not found!");
            return;
        }
        list.findFirst();
        while (true) {
            IEvent event = list.retrieve();
            if (event instanceof IWorkshop) {
                IWorkshop w = (IWorkshop) event;
                printStudentList(w.getParticipants());
            }
            if (list.last()) break;
            list.findNext();
        }
    }

    public void printAllEventsAlphabetically() {
        LinkedList<IEvent> list = events.getAllAlphabetically();
        printEventList(list);
    }

    private void printEventList(LinkedList<IEvent> list) {
        if (list.isEmpty()) {
            System.out.println("No events found!");
            return;
        }
        list.findFirst();
        while (true) {
            System.out.println(list.retrieve().toString());
            if (list.last()) break;
            list.findNext();
        }
    }
    
    public int[] getStudentIdsByNames(String namesStr) {
        String[] names = namesStr.split(",");
        int[] ids = new int[names.length];
        
        for (int i = 0; i < names.length; i++) {
            String name = names[i].trim();
            LinkedList<Student> matches = students.findByName(name);
            
            if (matches.isEmpty()) {
                System.out.println("❌ Student not found: " + name);
                return null;
            }
            
            matches.findFirst();
            ids[i] = matches.retrieve().getStudentId();
        }
        
        return ids;
    }
    
    public boolean loadEventsFromCSV(String eventsFilePath) {
        try {
            Scanner scanner = new Scanner(new File(eventsFilePath));
            if (scanner.hasNextLine()) scanner.nextLine(); 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",", -1);
                if (data.length >= 7) {
                    int eventId = Integer.parseInt(data[0].trim());
                    String title = data[1].trim();
                    String type = data[2].trim();
                    int studentId = Integer.parseInt(data[3].trim());
                    String location = data[6].trim();

                    String[] startParts = data[4].trim().split(" ");
                    String[] sDate = startParts[0].split("/");
                    String[] sTime = startParts[1].split(":");
                    DateTime start = new DateTime(Integer.parseInt(sDate[2]), Integer.parseInt(sDate[0]), Integer.parseInt(sDate[1]), Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]));

                    String[] endParts = data[5].trim().split(" ");
                    String[] eDate = endParts[0].split("/");
                    String[] eTime = endParts[1].split(":");
                    DateTime end = new DateTime(Integer.parseInt(eDate[2]), Integer.parseInt(eDate[0]), Integer.parseInt(eDate[1]), Integer.parseInt(eTime[0]), Integer.parseInt(eTime[1]));

                    IStudent student = students.findById(studentId);
                    if (student != null) {
                        if (hasConflict(studentId, start, end)) {
                            System.out.println("⚠️ Warning: Event '" + title + "' skipped (schedule conflict with student ID " + studentId + ")");
                            continue;
                        }
                        
                        if (type.equalsIgnoreCase("Meeting")) {
                            Meeting m = new Meeting(eventId, title, start, end, location, student);
                            if (events.addEvent(m)) {
                                student.getSchedule().add(m);
                            }
                        } else if (type.equalsIgnoreCase("Workshop")) {
                            boolean found = false;
                            LinkedList<IEvent> all = events.getAllAlphabetically();
                            if (!all.isEmpty()) {
                                all.findFirst();
                                while(true) {
                                    IEvent currEvent = all.retrieve();
                                    if (currEvent.getEventId() == eventId && currEvent instanceof IWorkshop) {
                                        ((IWorkshop)currEvent).addParticipant(student);
                                        student.getSchedule().add(currEvent);
                                        found = true;
                                        break;
                                    }
                                    if (all.last()) break;
                                    all.findNext();
                                }
                            }
                            if (!found) {
                                Workshop w = new Workshop(eventId, title, start, end, location);
                                w.addParticipant(student);
                                events.addEvent(w);
                                student.getSchedule().add(w);
                            }
                        }
                    }
                }
            }
            scanner.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
