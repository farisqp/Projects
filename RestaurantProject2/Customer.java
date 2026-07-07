
public class Customer extends Person {
    private int totalOrders;

    public Customer(String name, String id) {
        super(name, id);
        this.totalOrders = 0;
    }

    public void incOrders() { totalOrders++; }

    @Override
    public double computeSalary() {
        return 0;
    }
}
