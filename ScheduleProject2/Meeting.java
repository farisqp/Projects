public class Meeting extends Event implements IMeeting {
    private int advisorId;
    private int studentId;

    public Meeting(int id, ITimeSlot timeSlot, ILocation location, int advisorId, int studentId) {
        super(id, timeSlot, location, new BSTSet<>());
        this.advisorId = advisorId;
        this.studentId = studentId;
        getParticipantIds().insert(advisorId);
        getParticipantIds().insert(studentId);
    }

    public int getAdvisorId() { return advisorId; }
    public int getStudentId() { return studentId; }

    @Override
    public String toString() {
        return "Meeting{" + "id=" + getId() + ", advisor=" + advisorId + ", student=" + studentId + '}';
    }
}