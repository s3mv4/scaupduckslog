import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/** 
 * Logs
 * Handles the generation of the logs on the screen using randomness.
 * Obstacles for the duck to avoid.
 */
public class Logs extends Sprite{
    private final int logSize = 110;
    private BufferedImage logImage;
    private int logWidth;
    private int logHeight;
    private ArrayList<Point> logPoints= new ArrayList<>();
    private int windowWidth;
    private int windowHeight;

    public Logs(int width, int height) {
        windowWidth = width;
        windowHeight = height;
        try {
            logImage = ImageIO.read(getClass().getResource("/images/log.png"));

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
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("opa (log.png not found)");
        }   
    }

    @Override
    public void draw(Graphics g) {
        for (Point logPoint : logPoints ) {
            g.drawImage(logImage, logPoint.x, logPoint.y, logWidth, logHeight, null);
        }
    }

    // Randomly spawn 1-3 logs every second at the top of the window.
    public void addLogs() {
        double randomNum;
        int logAmount = 0;

        for (int i = 0; i < 4; i++) {
            randomNum = Math.random();
            if (randomNum >= 0.5) {
                logPoints.add(new Point(i*(windowWidth/4), 0 - logHeight));
                logAmount += 1;
            }
        }
        
        if (logAmount == 4) {
            randomNum = Math.round(Math.random()*3) + 1;
            logPoints.remove((int) (logPoints.size() - randomNum));
        } else if (logAmount == 0) {
            randomNum = Math.round(Math.random()*3);
            logPoints.add(new Point((int) randomNum*(windowWidth/4), 0 - logHeight));
        }
    }

    @Override
    public void update() {
        for (Point logPoint : logPoints) {
            logPoint.y += 3; 
        }

        ArrayList<Point> logPointsToRemove = new ArrayList<>();

        for (Point logPoint : logPoints) {
            if (logPoint.y >= windowHeight) {
                logPointsToRemove.add(logPoint);
            }
        }

        for (Point logPointToRemove : logPointsToRemove) {
            logPoints.remove(logPointToRemove);
        }
    }

    public ArrayList<Point> getLogPoints() {
        return logPoints;
    }

    public int getLogWidth() {
        return logWidth;
    }

    public int getLogHeight() {
        return logHeight;
    }

    @Override
    public void reset() {
        logPoints.clear();
    }
}
