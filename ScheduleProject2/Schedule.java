public class Schedule implements ISchedule {
    private BSTMap<ITimeSlot, Integer> events;
    private int size;

    public Schedule() {
        events = new BSTMap<>();
        size = 0;
    }

    public int size() { return size; }
    public boolean empty() { return size == 0; }

    public void clear() {
        events.clear();
        size = 0;
    }

    public boolean add(int eventId, ITimeSlot timeSlot) {
        if (events.get(timeSlot) != null) return false;
        
        Set<Integer> existingIds = getEventIds();
        if (existingIds.contains(eventId)) return false;
        
        if (conflicts(timeSlot)) return false;
        
        events.insert(timeSlot, eventId);
        size++;
        return true;
    }

    public boolean remove(int eventId) {
        List<ITimeSlot> keys = events.getKeys();
        if (keys.empty()) return false;
        keys.findFirst();
        while (true) {
            ITimeSlot ts = keys.retrieve();
            if (events.get(ts) == eventId) {
                events.remove(ts);
                size--;
                return true;
            }
            if (keys.last()) break;
            keys.findNext();
        }
        return false;
    }

    public boolean contains(int eventId) {
        List<ITimeSlot> keys = events.getKeys();
        if (keys.empty()) return false;
        keys.findFirst();
        while (true) {
            ITimeSlot ts = keys.retrieve();
            if (events.get(ts) == eventId) return true;
            if (keys.last()) break;
            keys.findNext();
        }
        return false;
    }

    public boolean conflicts(ITimeSlot timeSlot) {
        List<ITimeSlot> keys = events.getKeys();
        if (keys.empty()) return false;
        keys.findFirst();
        while (true) {
            ITimeSlot existing = keys.retrieve();
            if (timeSlot.compareTo(existing) == 0) return true;
            if (keys.last()) break;
            keys.findNext();
        }
        return false;
    }

    public Set<Integer> getEventIds() {
        Set<Integer> result = new BSTSet<>();
        List<ITimeSlot> keys = events.getKeys();
        if (!keys.empty()) {
            keys.findFirst();
            while (true) {
                ITimeSlot ts = keys.retrieve();
                result.insert(events.get(ts));
                if (keys.last()) break;
                keys.findNext();
            }
        }
        return result;
    }

    public Map<ITimeSlot, Integer> getEvents() {
        return events;
    }
}