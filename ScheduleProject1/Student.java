public class Student implements IStudent {
    private String name;
    private final int studentId; 
    private String email;
    private String major;
    private int yearLevel;
    private String notes;
    private LinkedList<IEvent> schedule; 

  
    public Student(int studentId, String name, String email, String major, int yearLevel, String notes) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.major = major;
        this.yearLevel = yearLevel;
        this.notes = notes;
        this.schedule = new LinkedList<>(); 
    }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public int getStudentId() { return studentId; }

    @Override
    public String getEmail() { return email; }

    @Override
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getMajor() { return major; }

    @Override
    public void setMajor(String major) { this.major = major; }

    @Override
    public int getYearLevel() { return yearLevel; }

    @Override
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }

    @Override
    public String getNotes() { return notes; }

    @Override
    public void setNotes(String notes) { this.notes = notes; }

    
    @Override
    public String getFirstName() {
        if (name == null || name.isEmpty()) return "";
        int spaceIndex = name.indexOf(' ');
        if (spaceIndex == -1) return name; 
        return name.substring(0, spaceIndex);
    }

    @Override
    public LinkedList<IEvent> getSchedule() { return schedule; }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Student ID: " + studentId + "\n" +
               "Email Address: " + email + "\n" +
               "Major: " + major + "\n" +
               "Year Level: " + yearLevel + "\n" +
               "Notes: " + notes;
    }

    
    @Override
    public int compareTo(IStudent other) {
        return Integer.compare(this.studentId, other.getStudentId());
    }
}