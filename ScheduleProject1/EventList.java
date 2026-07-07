public class EventList implements IEventList {
    private Node<IEvent> head;
    private int count;

    public EventList() {
        head = null;
        count = 0;
    }

    public boolean addEvent(IEvent event) {
        Node<IEvent> newNode = new Node<>(event);
        if (head == null || head.data.getTitle().compareToIgnoreCase(event.getTitle()) > 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<IEvent> current = head;
            while (current.next != null && current.next.data.getTitle().compareToIgnoreCase(event.getTitle()) <= 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        count++;
        return true;
    }

    public boolean removeEventById(int eventId) {
        if (head == null) return false;
        if (head.data.getEventId() == eventId) {
            head = head.next;
            count--;
            return true;
        }
        Node<IEvent> current = head;
        while (current.next != null) {
            if (current.next.data.getEventId() == eventId) {
                current.next = current.next.next;
                count--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public LinkedList<IEvent> getAllAlphabetically() {
        LinkedList<IEvent> result = new LinkedList<>();
        Node<IEvent> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    public LinkedList<IEvent> findByTitle(String title) {
        LinkedList<IEvent> result = new LinkedList<>();
        Node<IEvent> current = head;
        while (current != null) {
            if (current.data.getTitle().equals(title)) result.add(current.data);
            current = current.next;
        }
        return result;
    }

    public LinkedList<IEvent> findByStudentName(String studentFullName) {
        LinkedList<IEvent> result = new LinkedList<>();
        Node<IEvent> current = head;
        while (current != null) {
            boolean found = false;
            if (current.data instanceof IMeeting) {
                IMeeting m = (IMeeting) current.data;
                if (m.getStudent() != null && m.getStudent().getName().equals(studentFullName)) {
                    found = true;
                }
            } else if (current.data instanceof IWorkshop) {
                IWorkshop w = (IWorkshop) current.data;
                Node<Student> pCurrent = w.getParticipants().getHead();
                while (pCurrent != null) {
                    if (pCurrent.data.getName().equals(studentFullName)) {
                        found = true;
                        break;
                    }
                    pCurrent = pCurrent.next;
                }
            }
            if (found) result.add(current.data);
            current = current.next;
        }
        return result;
    }

    public int size() {
        return count;
    }
}