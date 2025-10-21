import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Logs extends JPanel {

    private BufferedImage logImage;
    private final int logHeight = 100;
    private final int logWidth = 25;
    private int[] xPosition = {0, 101, 202, 303};
    private int[] yPosition = {0, 0, 0, 0};

    Timer logTimer;
    private int movement = 0;
    private int speed = 3;


    public Logs() {

       
        
        try {
            logImage = ImageIO.read(getClass().getResource("/log.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("opa");
        }

        logTimer = new Timer(100, new ActionListener() { 

            @Override
            public void actionPerformed(ActionEvent e) {

                

                for (int i = 0; i < yPosition.length; i++) {
                    yPosition[i] += speed;
                
                    if (yPosition[i] > 700) {
                        yPosition[i] = 0;
                    }
                }

                if (movement != 0 && speed < 50){
                    speed += 2;
                    movement = movement + 1;
                }

                repaint();
            }
        });

        logTimer.start();

            
        

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
     

    public void draw(Graphics g) {

        for (int i = 0; i < xPosition.length; i++) {
            if (logImage != null) {
                g.drawImage(logImage, xPosition[i], yPosition[i], logHeight, logWidth, null);
            }
        }

    }

    public void update() {

    }

    
}
