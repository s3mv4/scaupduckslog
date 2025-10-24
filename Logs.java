import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Logs {
    private final int logSize = 110;
    private BufferedImage logImage;
    private int logHeight;
    private int logWidth;
    private ArrayList<Point> logPoints= new ArrayList<>();

    public Logs() {
        try {
            logImage = ImageIO.read(getClass().getResource("/log.png"));

            int originalWidth = logImage.getWidth();
            int originalHeight = logImage.getHeight();

            double aspectRatio = (double) originalWidth / originalHeight;

            if (aspectRatio < 1.0) {
                logHeight = logSize;
                logWidth = (int) (logSize * aspectRatio);
            } else {
                logWidth = logSize;
                logHeight = (int) (logSize / aspectRatio);
            }
            System.out.println(logHeight + ", " + logWidth);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("opa");
        }   
    }

    public void draw(Graphics g) {
        for (Point logPoint : logPoints ) {
            g.drawImage(logImage, logPoint.x, logPoint.y, logWidth, logHeight, null);
        }
    }

    public void addLogs() {
        double randomNum;
        int logAmount = 0;

        for (int i = 0; i < 4; i++) {
            randomNum = Math.random();
            if (randomNum >= 0.5) {
                logPoints.add(new Point(i*125, 0 - logHeight));
                logAmount += 1;
            }
        }
        
        if (logAmount == 4) {
            randomNum = Math.round(Math.random()*3) + 1;
            logPoints.remove((int)(logPoints.size() - randomNum));
        }
    }

    public void update() {
        for (Point logPoint : logPoints) {
            logPoint.y += 3; 
        }
    }

    public ArrayList<Point> getLogPoints() {
        return logPoints;
    }

    public void reset() {
        logPoints.clear();
    }
}
