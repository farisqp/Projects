public class Meeting extends Event implements IMeeting {
    private IStudent student;

    public Meeting(int eventId, String title, IDateTime startDateTime, IDateTime endDateTime, String location, IStudent student) {
        super(eventId, title, startDateTime, endDateTime, location);
        this.student = student;
    }

    public IStudent getStudent() { return student; }

    public void setStudent(IStudent student) { this.student = student; }

    public boolean hasStudent(int studentId) {
        return student != null && student.getStudentId() == studentId;
    }
    
}