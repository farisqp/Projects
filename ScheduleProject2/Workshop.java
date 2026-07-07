public class Workshop extends Event implements IWorkshop {
    private String title;
    private Set<Integer> advisorIds;
    private Set<Integer> studentIds;

    public Workshop(int id, String title, ITimeSlot timeSlot, ILocation location, 
                    Set<Integer> advisorIds, Set<Integer> studentIds) {
        super(id, timeSlot, location, new BSTSet<>());
        this.title = title;
        this.advisorIds = new BSTSet<>();
        this.studentIds = new BSTSet<>();
        
        List<Integer> advisorList = advisorIds.getKeys();
        if (!advisorList.empty()) {
            advisorList.findFirst();
            while (true) {
                int aid = advisorList.retrieve();
                this.advisorIds.insert(aid);
                getParticipantIds().insert(aid);
                if (advisorList.last()) break;
                advisorList.findNext();
            }
        }
        
        List<Integer> studentList = studentIds.getKeys();
        if (!studentList.empty()) {
            studentList.findFirst();
            while (true) {
                int sid = studentList.retrieve();
                this.studentIds.insert(sid);
                getParticipantIds().insert(sid);
                if (studentList.last()) break;
                studentList.findNext();
            }
        }
    }

    public String getTitle() { return title; }
    public Set<Integer> getAdvisorIds() { return advisorIds; }
    public Set<Integer> getStudentIds() { return studentIds; }

    @Override
    public String toString() {
        return "Workshop{" + "id=" + getId() + ", title='" + title + '\'' + '}';
    }
}