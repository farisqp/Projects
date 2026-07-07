public interface IStudent extends Comparable<IStudent> {
    String getName();
    void setName(String name);
    int getStudentId();
    String getEmail();
    void setEmail(String email);
    String getMajor();
    void setMajor(String major);
    int getYearLevel();
    void setYearLevel(int yearLevel);
    String getNotes();
    void setNotes(String notes);
    String getFirstName();
    LinkedList<IEvent> getSchedule();
    String toString();
    int compareTo(IStudent other);
}