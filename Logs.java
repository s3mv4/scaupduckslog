import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.TimerTask;




public class Logs extends JPanel {

    private BufferedImage logImage;
    private final int logHeight = 110;
    private final int logWidth = 25;
    private ArrayList<Point> logPoints= new ArrayList<>();

    private Timer logGameTimer;
    private Timer logSpawTimer;

    public Logs() {


        try {
            logImage = ImageIO.read(getClass().getResource("/log.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("opa");
        }   

        addLogs();

        Timer logSpawnTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addLogs();
                
            }
        });
        logSpawnTimer.start();
    
        logSpawnTimer.setRepeats(true);
        logSpawnTimer.setCoalesce(true); 

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
     

    public void draw(Graphics g) {
        
        for (Point logPoint : logPoints ) {
            g.drawImage(logImage, logPoint.x, logPoint.y, logHeight, logWidth, null);

        }


    }

    public void addLogs() {
        double randomNum;
        int logAmount = 0;

        for (int i = 0; i < 4; i++) {
            randomNum = Math.random();
            if (randomNum >= 0.5) {
                logPoints.add(new Point(i*125, 0));
                logAmount += 1;
            }
        }
        
        if (logAmount == 4) {
            System.out.println("Digga");
            randomNum = Math.round(Math.random()*3) + 1;
            logPoints.remove((int)(logPoints.size() - randomNum));
        }
    }

    public void update() {
        for (Point logPoint : logPoints) {
            logPoint.y += 5; 
        }
    }

    
}
