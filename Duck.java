import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Duck extends Sprite {
    private final int duckSize = 100;
    private final Point duckPoint;
    private double duckX;
    private double duckY;
    private BufferedImage duckImage;
    private BufferedImage flippedDuckImage;
    private double movementAngle = 0;
    private double rotationAngle = 0;
    private int duckWidth;
    private int duckHeight;
    private boolean gameOver = false;
    private int windowWidth;
    private int windowHeight;
    private Bread bread;
    private Logs logs;
    private int leftHitboxBuffer = 20;
    private int topHitboxBuffer = 40;
    private int rightHitboxBuffer = 10;
    private int bottomHitboxBuffer = 12;
    private Shape rotatedHitbox;
    private boolean flipped = false;

    public Duck(int width, int height) {
        windowWidth = width;
        windowHeight = height;
        duckX = windowWidth/2;
        duckY = windowHeight/2;
        duckPoint = new Point((int) duckX, (int) duckY);

        try {
            duckImage = ImageIO.read(getClass().getResource("/images/duck3.png"));

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-duckImage.getWidth(), 0);

            flippedDuckImage = new AffineTransformOp(
                tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR
            ).filter(duckImage, null);

            int originalWidth = duckImage.getWidth();
            int originalHeight = duckImage.getHeight();

            double aspectRatio = (double) originalWidth / originalHeight;

            if (aspectRatio < 1.0) {
                duckHeight = duckSize;
                duckWidth = (int) (duckSize * aspectRatio);
            } else {
                duckWidth = duckSize;
                duckHeight = (int) (duckSize / aspectRatio);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ducky dead :( no quakking (duck3.png not found)");
        }
    }

    @Override
    public void draw(Graphics g) {
        if (duckImage == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        // Translate to the center of the duck
        g2d.translate(duckPoint.x, duckPoint.y);

        // Apply rotation (rotates around origin which is now duck center)
        g2d.rotate(rotationAngle);

        // Duck already translated so no need for duckPoint.x and duckPoint.y
        if (rotationAngle > 0 && rotationAngle < Math.PI) {
            flipped = false;
            g2d.drawImage(duckImage, - duckWidth / 2, - duckHeight / 2, duckWidth, duckHeight, null);
        } else {
            flipped = true;
            g2d.drawImage(flippedDuckImage, - duckWidth / 2, - duckHeight / 2, duckWidth, duckHeight, null);
        }

        g2d.dispose();

        // Graphics2D g2d2 = (Graphics2D) g.create();
        // g2d2.setColor(new Color(255, 0, 0));
        // g2d2.draw(rotatedHitbox);
        // g2d2.dispose();

    }

    public Point findClosestBread(int index, Point minimalPoint, double minimalDistance, LinkedList<Point> breadPoints) {
        if (index > breadPoints.size() - 1) {
            return minimalPoint;
        }

        Point breadPoint = breadPoints.get(index);
        double distance = Math.sqrt(Math.pow((breadPoint.x - duckPoint.x), 2) + Math.pow((breadPoint.y - duckPoint.y), 2));

        if (distance < minimalDistance) {
            minimalDistance = distance;
            minimalPoint = breadPoint;
        }

        return findClosestBread(index + 1, minimalPoint, minimalDistance, breadPoints);
    }

    public void rotateDuck(Bread bread) {
        LinkedList<Point> breadPoints = bread.getBreadPoints();
        Point minimalPoint;

        duckY += 3;

        if (breadPoints == null || breadPoints.isEmpty()) {
            return;
        }

        minimalPoint = findClosestBread(0, null, Double.MAX_VALUE, breadPoints);

        // rotate duck towards minimalPoint
        movementAngle = Math.atan2(minimalPoint.y - duckPoint.y, minimalPoint.x - duckPoint.x);
        rotationAngle = movementAngle + Math.PI / 2;

        duckX += Math.cos(movementAngle) * 4;
        duckY += Math.sin(movementAngle) * 4;
    }

    public void checkBreadCollision(Bread bread) {
        LinkedList<Point> breadPoints = bread.getBreadPoints();
        Point removePoint = null;

        for (Point breadPoint : breadPoints) {
            if (breadPoint.x < duckX + duckWidth / 2
                && breadPoint.x > duckX - duckWidth / 2
                && breadPoint.y < duckY + duckHeight / 2
                && breadPoint.y > duckY - duckHeight / 2) {
                    removePoint = breadPoint;
                }
        }

        if (removePoint != null) {
            breadPoints.remove(removePoint);
        }
    }

    public void checkLogsCollision(Logs logs) {
        ArrayList<Point> logPoints = logs.getLogPoints();

        for (Point logPoint : logPoints) {
            Rectangle logRect = new Rectangle(logPoint.x, logPoint.y, logs.getLogWidth(), logs.getLogHeight());
            if (rotatedHitbox.intersects(logRect)) {
                    gameOver = true;
            }
        }
    }
    
    public boolean getGameOver() {
        return gameOver;
    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }

    public void setLogs(Logs logs) {
        this.logs = logs;
    }
    
    public void updateHitbox() {
        int leftBuffer;
        int rightBuffer;

        if (flipped) {
            leftBuffer = rightHitboxBuffer;
            rightBuffer = leftHitboxBuffer;
        } else {
            leftBuffer = leftHitboxBuffer;
            rightBuffer = rightHitboxBuffer;
        }

        double hitboxWidth = duckWidth - leftBuffer - rightBuffer;
        double hitboxHeight = duckHeight - topHitboxBuffer - bottomHitboxBuffer;

        // top left corner
        double hitboxX = leftBuffer - duckWidth / 2;
        double hitboxY = topHitboxBuffer - duckHeight / 2;

        Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX, hitboxY, hitboxWidth, hitboxHeight);

        AffineTransform transform = new AffineTransform();
        transform.translate(duckX, duckY);
        transform.rotate(rotationAngle);

        rotatedHitbox = transform.createTransformedShape(hitbox);
    }

    @Override
    public void update() {
        rotateDuck(bread);
        duckPoint.setLocation((int) duckX, (int) duckY);

        updateHitbox();
        checkBreadCollision(bread);
        checkLogsCollision(logs);

        if (duckY - duckHeight / 2 >= windowHeight) {
            gameOver = true;
        }
    }

    @Override
    public void reset() {
        duckX = windowWidth/2;
        duckY = windowHeight/2;
        duckPoint.setLocation((int) duckX, (int) duckY);
        movementAngle = 0;
        rotationAngle = 0;
        gameOver = false;
    }
}