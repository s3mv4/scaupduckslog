import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bread bread;
    private Duck duck;
    public int frameRate = 60;

    public GamePanel() {
        setBackground(new Color(15, 50, 180));

        Timer timer = new Timer(1000/frameRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bread != null) {
                    bread.update();
                }
                if (duck != null) {
                    duck.update(bread);
                }
                repaint();
            }
        });
        timer.start();
    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }

    public void setDuck(Duck duck) {
        this.duck = duck;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bread != null) {
            bread.draw(g);
        }
        if (duck != null) {
            duck.draw(g);
        }
    }
}