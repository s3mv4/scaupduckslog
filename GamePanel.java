import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bread bread;
    private Logs logs;
    public int frameRate = 60;

    public GamePanel() {
        setBackground(new Color(15, 50, 180));
        

        Timer timer = new Timer(1000/frameRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bread != null) {
                    bread.update();
                    repaint();
                }

                if (logs != null) {
                    logs.update();
                    repaint();
                }

            }
        });
        timer.start();


    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }


    public void setLogs(Logs logs) {
        this.logs = logs;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bread != null) {
            bread.draw(g);
        }

        if (logs != null) {
            logs.draw(g);
        }


    }
}