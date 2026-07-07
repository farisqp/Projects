public class Event implements IEvent {
    private int id;
    private ITimeSlot timeSlot;
    private ILocation location;
    private Set<Integer> participantIds;

    public Event(int id, ITimeSlot timeSlot, ILocation location, Set<Integer> participantIds) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.location = location;
        this.participantIds = participantIds;
    }

    public int getId() { return id; }
    public ITimeSlot getTimeSlot() { return timeSlot; }
    public ILocation getLocation() { return location; }
    public Set<Integer> getParticipantIds() { return participantIds; }

    public int compareTo(IEvent other) {
        return Integer.compare(this.id, other.getId());
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", location=" + location.getName() + '}';
    }
}