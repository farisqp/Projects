
public class Waiter extends Employee {
    private int tips;

    public Waiter(String name, String id, double baseSalary) {
        super(name, id, baseSalary);
        this.tips = 0;
    }

    public void addTip(int t) { tips += t; }

    @Override
    public double computeSalary() {
        return baseSalary + tips;
    }
}
