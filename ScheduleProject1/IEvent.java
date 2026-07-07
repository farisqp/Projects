public interface IEvent extends Comparable<IEvent>  {
    int getEventId();
    String getTitle();
    void setTitle(String title);
    IDateTime getStartDateTime();
    IDateTime getEndDateTime();
    String getLocation();
    void setLocation(String location);
    boolean hasStudent(int studentId);
    String toString();
    int compareTo(IEvent other);
}