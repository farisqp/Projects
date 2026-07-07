public interface IAdvisingSystem {
    boolean loadStudentsFromCSV(String studentsFilePath);
    boolean loadEventsFromCSV(String eventsFilePath);
    boolean addStudent(IStudent student);
    IStudent searchStudentById(int studentId);
    IStudent searchStudentByEmail(String email);
    void printStudentsByMajor(String major);
    void printStudentsByYearLevel(int yearLevel);
    void printStudentsByName(String fullName);
    void printStudentsByPartialName(String partialName);
    void printAllStudents();
    boolean deleteStudent(int studentId);
    boolean scheduleMeeting(String title, IDateTime startDateTime, IDateTime endDateTime, String location, int studentId);
    boolean scheduleWorkshop(String title, IDateTime startDateTime, IDateTime endDateTime, String location, int[] studentIds);
    void printEventDetailsByTitle(String title);
    void printEventDetailsByStudentName(String studentName);
    void printWorkshopStudents(String workshopTitle);
    void printAllEventsAlphabetically();
}