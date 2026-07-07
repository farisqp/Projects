import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class RestaurantGUI extends JFrame {

    private Restaurant restaurant;
    private JTextField nameField, priceField;
    private JButton addButton, showButton;
    private JButton saveButton, loadButton;


    public RestaurantGUI(Restaurant restaurant) {
        super("Add Dish");
        this.restaurant = restaurant;

        setLayout(new GridLayout(5, 2));
        
        saveButton = new JButton("Save Menu");
        loadButton = new JButton("Load Menu");

        add(saveButton);
        add(loadButton);

        add(new JLabel("Dish Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        addButton = new JButton("Add Dish");
        showButton = new JButton("Show Menu");

        add(addButton);
        add(showButton);

        addButton.addActionListener(e -> addDish());
        showButton.addActionListener(e -> new MenuFrame(restaurant));
        
        saveButton.addActionListener(e -> {
            try {
                restaurant.saveMenuToFile();
                JOptionPane.showMessageDialog(this, "Menu saved to file.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file.");
            }
        });

        loadButton.addActionListener(e -> {
            try {
                restaurant.loadMenuFromFile();
                JOptionPane.showMessageDialog(this, "Menu loaded from file.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading file.");
            }
        });


        setSize(300, 200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addDish() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());

            Dish d = new Dish(name, price);
            restaurant.addDish(d);

            JOptionPane.showMessageDialog(this, "Dish added");
        }
        catch (InvalidDishException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number");
        }
    }
}
