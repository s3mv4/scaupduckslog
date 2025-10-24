import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bread bread;
    private Duck duck;
    private Logs logs;
    public int frameRate = 60;
    private int logCounter;

    public GamePanel() {
        setBackground(new Color(15, 50, 180));

        Timer timer = new Timer(1000/frameRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (duck != null && duck.getGameOver()) {
                    ((Timer)e.getSource()).stop();
                    return;
                }

                if (bread != null) {
                    bread.update();
                }

                if (duck != null) {
                    duck.update(bread, logs);
                }

                if (logs != null) {
                    logs.update();
                }

                logCounter += 1;

                if (logCounter == 60) {
                    logCounter = 0;
                    logs.addLogs();
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

    public void setLogs(Logs logs) {
        this.logs = logs;
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

        if (logs != null) {
            logs.draw(g);
        }
    }
}