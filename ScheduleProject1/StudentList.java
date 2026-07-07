public class StudentList implements IStudentList {
    private Node<IStudent> head;
    private int count;

    public StudentList() {
        head = null;
        count = 0;
    }

    public boolean add(IStudent student) {
        if (findById(student.getStudentId()) != null) return false;

        Node<IStudent> newNode = new Node<>(student);
        if (head == null || head.data.getStudentId() > student.getStudentId()) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<IStudent> current = head;
            while (current.next != null && current.next.data.getStudentId() < student.getStudentId()) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        count++;
        return true;
    }

    public IStudent findById(int studentId) {
        Node<IStudent> current = head;
        while (current != null) {
            if (current.data.getStudentId() == studentId) return current.data;
            current = current.next;
        }
        return null;
    }

    public LinkedList<Student> findByName(String fullName) {
        LinkedList<Student> result = new LinkedList<>();
        Node<IStudent> current = head;
        while (current != null) {
            if (current.data.getName().equals(fullName)) result.add((Student) current.data);
            current = current.next;
        }
        return result;
    }

    public LinkedList<Student> findByNameContains(String partialName) {
        LinkedList<Student> result = new LinkedList<>();
        Node<IStudent> current = head;
        String lowerPartial = partialName.toLowerCase();
        while (current != null) {
            if (current.data.getName().toLowerCase().contains(lowerPartial)) result.add((Student) current.data);
            current = current.next;
        }
        return result;
    }

    public IStudent findByEmail(String email) {
        Node<IStudent> current = head;
        while (current != null) {
            if (current.data.getEmail().equals(email)) return current.data;
            current = current.next;
        }
        return null;
    }

    public LinkedList<Student> findByMajor(String major) {
        LinkedList<Student> result = new LinkedList<>();
        Node<IStudent> current = head;
        while (current != null) {
            if (current.data.getMajor().equals(major)) result.add((Student) current.data);
            current = current.next;
        }
        return result;
    }

    public LinkedList<Student> findByYearLevel(int yearLevel) {
        LinkedList<Student> result = new LinkedList<>();
        Node<IStudent> current = head;
        while (current != null) {
            if (current.data.getYearLevel() == yearLevel) result.add((Student) current.data);
            current = current.next;
        }
        return result;
    }

    public LinkedList<Student> getAll() {
        LinkedList<Student> result = new LinkedList<>();
        Node<IStudent> current = head;
        while (current != null) {
            result.add((Student) current.data);
            current = current.next;
        }
        return result;
    }

    public boolean deleteById(int studentId) {
        if (head == null) return false;
        if (head.data.getStudentId() == studentId) {
            head = head.next;
            count--;
            return true;
        }
        Node<IStudent> current = head;
        while (current.next != null) {
            if (current.next.data.getStudentId() == studentId) {
                current.next = current.next.next;
                count--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return count;
    }
}