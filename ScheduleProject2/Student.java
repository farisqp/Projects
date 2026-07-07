public class Student extends Person implements IStudent {
    private String major;

    public Student(int id, String name, String email, ISchedule schedule, String major) {
        super(id, name, email, schedule);
        this.major = major;
    }

    public String getMajor() { return major; }

    @Override
    public String toString() {
        return "Student{" + "id=" + getId() + ", name='" + getName() + '\'' + ", email='" + getEmail() + '\'' + ", major='" + major + '\'' + '}';
    }
}