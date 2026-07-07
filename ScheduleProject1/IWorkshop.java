public interface IWorkshop extends IEvent {
    LinkedList<Student> getParticipants();
    boolean addParticipant(IStudent student);
    boolean removeParticipantById(int studentId);
    boolean isEmpty();
}