public class Workshop extends Event implements IWorkshop {
    private LinkedList<Student> participants;

    public Workshop(int eventId, String title, IDateTime startDateTime, IDateTime endDateTime, String location) {
        super(eventId, title, startDateTime, endDateTime, location);
        this.participants = new LinkedList<>();
    }

    public LinkedList<Student> getParticipants() { return participants; }

    public boolean addParticipant(IStudent student) {
        if (hasStudent(student.getStudentId())) return false;
        participants.add((Student) student);
        return true;
    }

    public boolean removeParticipantById(int studentId) {
        if (participants.isEmpty()) return false;
        participants.findFirst();
        while (true) {
            if (participants.retrieve().getStudentId() == studentId) {
                participants.remove();
                return true;
            }
            if (participants.last()) break;
            participants.findNext();
        }
        return false;
    }

    public boolean isEmpty() { return participants.isEmpty(); }

    public boolean hasStudent(int studentId) {
        Node<Student> current = participants.getHead();
        while (current != null) {
            if (current.data.getStudentId() == studentId) return true;
            current = current.next;
        }
        return false;
    }
    public String toString() {
        String names = "";
        if (!participants.isEmpty()) {
            participants.findFirst();
            while (true) {
                names += participants.retrieve().getName();
                if (participants.last()) break;
                names += ", ";
                participants.findNext();
            }
        }
        return " Workshop title: " + getTitle() + "\n" +
               " Student name(s): " + names + "\n" +
               " Workshop date and time (MM/DD/YYYY HH:MM): " + getStartDateTime().format() + "\n" +
               " Workshop location: " + getLocation();
    }
    
}