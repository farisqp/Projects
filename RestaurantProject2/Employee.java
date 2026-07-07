
abstract class Employee extends Person {
    protected double baseSalary;

    public Employee(String name, String id, double baseSalary) {
        super(name, id);
        this.baseSalary = baseSalary;
    }
}

