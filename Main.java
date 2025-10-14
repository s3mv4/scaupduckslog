import java.awt.*;
import javax.swing.*;

public class Main {
    void update() {
        JFrame frame = new JFrame ("Scaup Ducks Log");
        JPanel panel = new JPanel();

        Bread bread = new Bread();
        panel.addMouseListener(bread);

        frame.add(panel);
        panel.setBackground(new Color(0, 0, 0));
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main().update();
    }
}
