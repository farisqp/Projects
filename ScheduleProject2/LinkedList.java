public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> current;

    public LinkedList() {
        head = current = null;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current != null && current.next == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        if (current != null) current = current.next;
    }

    public T retrieve() {
        return current != null ? current.data : null;
    }

    public void update(T val) {
        if (current != null) current.data = val;
    }

    public void insert(T val) {
        Node<T> newNode = new Node<>(val);
        if (empty()) {
            head = current = newNode;
        } else {
            Node<T> tmp = current.next;
            current.next = newNode;
            current = newNode;
            current.next = tmp;
        }
    }

    public void remove() {
        if (current == null) return;
        if (current == head) {
            head = head.next;
        } else {
            Node<T> tmp = head;
            while (tmp != null && tmp.next != current) tmp = tmp.next;
            if (tmp != null) tmp.next = current.next;
        }
        if (current.next == null) current = head;
        else current = current.next;
    }
}

class Node<T> {
    public T data;
    public Node<T> next;

    public Node(T val) {
        data = val;
        next = null;
    }
}