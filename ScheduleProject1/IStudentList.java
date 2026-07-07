public interface IStudentList {
    boolean add(IStudent student);
    IStudent findById(int studentId);
    LinkedList<Student> findByName(String fullName);
    LinkedList<Student> findByNameContains(String partialName);
    IStudent findByEmail(String email);
    LinkedList<Student> findByMajor(String major);
    LinkedList<Student> findByYearLevel(int yearLevel);
    LinkedList<Student> getAll();
    boolean deleteById(int studentId);
    int size();
}