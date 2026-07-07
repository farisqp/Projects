import java.util.LinkedList;

public class Order {
    private LinkedList<Dish> items;

    public Order() {
        items = new LinkedList<>();
    }

    public void addDish(Dish d) {
        items.add(d);
    }

    public LinkedList<Dish> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (Dish d : items) total += d.getPrice();
        return total;
    }
}
