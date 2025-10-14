import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bread bread;

    public GamePanel() {
        setBackground(new Color(15, 50, 180));
    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bread != null) {
            bread.draw(g);
        }
    }
}