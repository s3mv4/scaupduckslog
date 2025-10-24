import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bread bread;
    private Duck duck;
    private Logs logs;
    public int frameRate = 60;
    private int logCounter;
    private BufferedImage deadDuckImage;
    private boolean drawDeadDuck = false;
    private float deadDuckAlpha = 0f;

    public GamePanel() {
        setBackground(new Color(15, 50, 180));
        setFocusable(true);

        try {
            deadDuckImage = ImageIO.read(getClass().getResource("/duckdead.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ducky will live another day (duckdead.png not found)");
        }

        Timer timer = new Timer(1000/frameRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (duck != null && duck.getGameOver()) {
                    drawDeadDuck = true;
                } else {
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
                }

                if (drawDeadDuck && deadDuckAlpha < 1f) {
                    deadDuckAlpha += 0.005f;
                    if (deadDuckAlpha >= 1.0f) {
                        deadDuckAlpha = 1f;
                    }
                }

                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    drawDeadDuck = false;
                    deadDuckAlpha = 0f;

                    if (logs != null) {
                        logs.reset();
                    }

                    if (bread != null) {
                        bread.reset();
                    }

                    if (duck != null) {
                        duck.reset();
                    }
                }
            }
        });
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

        if (drawDeadDuck && deadDuckImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(new Color(0, 0, 0));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, deadDuckAlpha));
            g2d.drawImage(deadDuckImage, 
                getWidth()/2 - deadDuckImage.getWidth(), 
                getHeight()/2 - deadDuckImage.getHeight(), 
                deadDuckImage.getWidth()*2, 
                deadDuckImage.getHeight()*2, 
                null);

            g2d.dispose();
        }
    }
}