public class Person implements IPerson {
    private int id;
    private String name;
    private String email;
    private ISchedule schedule;

    public Person(int id, String name, String email, ISchedule schedule) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.schedule = schedule;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public ISchedule getSchedule() { return schedule; }

    public int compareTo(IPerson other) {
        return Integer.compare(this.id, other.getId());
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}