import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    public MenuFrame(Restaurant restaurant) {
        super("Menu");

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area));

        for (Dish d : restaurant.getMenu()) {
            area.append(d.toString() + "\n");
        }

        setSize(300, 350);
        setVisible(true);
    }
}
