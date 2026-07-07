public interface IEventList {
    boolean addEvent(IEvent event);
    boolean removeEventById(int eventId);
    LinkedList<IEvent> getAllAlphabetically();
    LinkedList<IEvent> findByTitle(String title);
    LinkedList<IEvent> findByStudentName(String studentFullName);
    int size();
}