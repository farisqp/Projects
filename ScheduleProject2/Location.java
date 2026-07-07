public class Location implements ILocation {
    private int id;
    private String name;
    private boolean reservable;
    private boolean online;
    private int capacity;
    private ISchedule schedule;

    public Location(int id, String name, boolean reservable, boolean online, int capacity, ISchedule schedule) {
        this.id = id;
        this.name = name;
        this.reservable = reservable;
        this.online = online;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isReservable() { return reservable; }
    public ISchedule getSchedule() { return schedule; }
    public int getCapacity() { return capacity; }
    public boolean isOnline() { return online; }

    public int compareTo(ILocation other) {
        return Integer.compare(this.id, other.getId());
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name='" + name + '\'' + ", capacity=" + capacity + '}';
    }
}