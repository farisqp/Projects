public class Main {
    public static void main(String[] args) {
        AdvisingSystemPhase2 system = new AdvisingSystemPhase2();

        try {
            ISchedule advisorSchedule1 = new Schedule();
            ISchedule advisorSchedule2 = new Schedule();
            ISchedule studentSchedule1 = new Schedule();
            ISchedule studentSchedule2 = new Schedule();
            ISchedule studentSchedule3 = new Schedule();
            ISchedule locationSchedule = new Schedule();

            ILocation officeA = new Location(1, "Office A", false, false, 1, new Schedule());
            ILocation officeB = new Location(2, "Office B", false, false, 1, new Schedule());
            ILocation classroom = new Location(3, "Classroom 101", true, false, 30, locationSchedule);

            system.addLocation(officeA);
            system.addLocation(officeB);
            system.addLocation(classroom);

            IAdvisor advisor1 = new Advisor(101, "Dr. Ahmed", "ahmed@ksu.edu.sa", advisorSchedule1, officeA);
            IAdvisor advisor2 = new Advisor(102, "Dr. Fatima", "fatima@ksu.edu.sa", advisorSchedule2, officeB);

            system.addAdvisor(advisor1);
            system.addAdvisor(advisor2);

            IStudent student1 = new Student(1001, "Ali Mohammed", "ali@ksu.edu.sa", studentSchedule1, "Computer Science");
            IStudent student2 = new Student(1002, "Sara Ahmed", "sara@ksu.edu.sa", studentSchedule2, "Information Technology");
            IStudent student3 = new Student(1003, "Noor Hassan", "noor@ksu.edu.sa", studentSchedule3, "Cybersecurity");

            system.addStudent(student1);
            system.addStudent(student2);
            system.addStudent(student3);

            System.out.println("=== System Initialized ===");
            System.out.println("Advisors: " + system.getAdvisors().size());
            System.out.println("Students: " + system.getStudents().size());
            System.out.println("Locations: " + system.getLocations().size());

            DateTime meetingStart = new DateTime(2026, 5, 20, 10, 0);
            DateTime meetingEnd = new DateTime(2026, 5, 20, 11, 0);
            ITimeSlot meetingSlot = new TimeSlot(meetingStart, meetingEnd);

            int meetingId = system.scheduleMeeting(meetingSlot, 101, 1001);
            System.out.println("\n=== Meeting Scheduled ===");
            System.out.println("Meeting ID: " + meetingId);
            System.out.println("Advisor: Dr. Ahmed");
            System.out.println("Student: Ali Mohammed");
            System.out.println("Time: " + meetingStart.format() + " - " + meetingEnd.format());

            DateTime workshopStart = new DateTime(2026, 5, 22, 14, 0);
            DateTime workshopEnd = new DateTime(2026, 5, 22, 16, 0);
            ITimeSlot workshopSlot = new TimeSlot(workshopStart, workshopEnd);

            int[] advisorIds = {101, 102};
            int[] studentIds = {1001, 1002, 1003};

            int workshopId = system.scheduleWorkshop("Advanced Java Programming", workshopSlot, 3, advisorIds, studentIds);
            System.out.println("\n=== Workshop Scheduled ===");
            System.out.println("Workshop ID: " + workshopId);
            System.out.println("Title: Advanced Java Programming");
            System.out.println("Time: " + workshopStart.format() + " - " + workshopEnd.format());
            System.out.println("Location: Classroom 101");
            System.out.println("Advisors: 2");
            System.out.println("Students: 3");

            System.out.println("\n=== System Statistics ===");
            System.out.println("Total Meetings: " + system.getMeetings().size());
            System.out.println("Total Workshops: " + system.getWorkshops().size());
            System.out.println("Total Events: " + system.getEvents().size());

            system.removeStudentFromWorkshop(workshopId, 1002);
            System.out.println("\n=== Student Removed from Workshop ===");
            System.out.println("Removed: Sara Ahmed");
            System.out.println("Workshop Students: 2");

            DateTime conflictStart = new DateTime(2026, 5, 20, 10, 30);
            DateTime conflictEnd = new DateTime(2026, 5, 20, 11, 30);
            ITimeSlot conflictSlot = new TimeSlot(conflictStart, conflictEnd);

            try {
                system.scheduleMeeting(conflictSlot, 101, 1001);
                System.out.println("\nMeeting scheduled successfully");
            } catch (SchedulingException e) {
                System.out.println("\n=== Scheduling Conflict Detected ===");
                System.out.println("Reason: " + e.getReason());
            }

            boolean deleted = system.deleteStudent(1002);
            System.out.println("\n=== Student Deletion ===");
            System.out.println("Student Deleted: " + deleted);
            System.out.println("Remaining Students: " + system.getStudents().size());

            boolean cancelledMeeting = system.cancelMeeting(meetingId);
            System.out.println("\n=== Meeting Cancelled ===");
            System.out.println("Meeting Cancelled: " + cancelledMeeting);
            System.out.println("Remaining Meetings: " + system.getMeetings().size());

            System.out.println("\n=== Final Statistics ===");
            System.out.println("Total Advisors: " + system.getAdvisors().size());
            System.out.println("Total Students: " + system.getStudents().size());
            System.out.println("Total Locations: " + system.getLocations().size());
            System.out.println("Total Events: " + system.getEvents().size());

        } catch (SchedulingException e) {
            System.out.println("Error: " + e.getReason());
        }
    }
}