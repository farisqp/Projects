public abstract class Event implements IEvent {
    private int eventId;
    private String title;
    private IDateTime startDateTime;
    private IDateTime endDateTime;
    private String location;

    public Event(int eventId, String title, IDateTime startDateTime, IDateTime endDateTime, String location) {
        this.eventId = eventId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
    }

    public int getEventId() { return eventId; }
    
    public String getTitle() { return title; }
    
    public void setTitle(String title) { this.title = title; }
    
    public IDateTime getStartDateTime() { return startDateTime; }
    
    public IDateTime getEndDateTime() { return endDateTime; }
    
    public String getLocation() { return location; }
    
    public void setLocation(String location) { this.location = location; }

    public abstract boolean hasStudent(int studentId);

    public String toString() {
        return String.format("Event ID: %d | Title: %s | Start: %s | End: %s | Location: %s",
                eventId, title, startDateTime.format(), endDateTime.format(), location);
    }

    public int compareTo(IEvent other) {
        return this.title.compareToIgnoreCase(other.getTitle());
    }
}