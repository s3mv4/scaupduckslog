import java.awt.*;
import javax.swing.*;
public class Main {
    void update() {
        JFrame frame = new JFrame ("Scaup Ducks Log");
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setBackground(new Color(153, 247, 98));
        frame.setSize(500, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main().update();
    }
}
