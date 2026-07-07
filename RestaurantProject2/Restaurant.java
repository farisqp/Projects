import java.io.*;
import java.util.LinkedList;

public class Restaurant {

    private LinkedList<Person> people;
    private LinkedList<Dish> menu;
    private LinkedList<Order> orders;

    public Restaurant() {
        people = new LinkedList<>();
        menu = new LinkedList<>();
        orders = new LinkedList<>();
    }

    public void addPerson(Person p) {
        people.add(p);
    }

    public void addDish(Dish d) {
        menu.add(d);
    }

    public LinkedList<Dish> getMenu() {
        return menu;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    public void saveMenuToFile() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("menu.txt"));
        for (Dish d : menu) {
            out.println(d.getName() + "," + d.getPrice());
        }
        out.close();
    }


    public void loadMenuFromFile() throws IOException {
        menu.clear();
        File file = new File("menu.txt");

        if (!file.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            try {
                menu.add(new Dish(parts[0], Double.parseDouble(parts[1])));
            } catch (InvalidDishException ex) {
                System.out.println("Invalid dish found in file.");
            }
        }

        br.close();
    }
}
