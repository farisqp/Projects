public class LinkedList<T> implements List<T>{
    private Node<T> head;
    private Node<T> current;

    public LinkedList() {
        head = current = null;
    }

    public boolean isEmpty() { return head == null; }
    public boolean last() { return current != null && current.next == null; }
    public boolean full() { return false; }
    public void findFirst() { current = head; }
    public void findNext() { current = current.next; }
    public T retrieve() { return current.data; }
    public void update(T val) { current.data = val; }
    
    public void insert(T val) {
        Node<T> tmp;
        if (isEmpty()) {
            current = head = new Node<T>(val);
        } else {
            tmp = current.next;
            current.next = new Node<T>(val);
            current = current.next;
            current.next = tmp;
        }
    }
    
    public void remove() {
        if (current == head) {
            head = head.next;
        } else {
            Node<T> tmp = head;
            while (tmp.next != current) tmp = tmp.next;
            tmp.next = current.next;
        }
        if (current != null && current.next == null) current = head;
        else if (current != null) current = current.next;
    }
    
    public int size() {
        int count = 0;
        Node<T> p = head;
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }
    public Node<T> getHead() {
        return head;
    }
    public void add(T val) {
        if (head == null) {
            head = current = new Node<T>(val);
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new Node<T>(val);
        }
    }
}