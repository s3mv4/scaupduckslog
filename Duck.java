import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Duck {
    private final int duckSize = 100;
    private final Point duckPoint;
    private double duckX;
    private double duckY;
    private BufferedImage duckImage;
    private BufferedImage flippedDuckImage;
    private double movementAngle = 0;
    private double rotationAngle = 0;
    int duckWidth;
    int duckHeight;
    private boolean gameOver = false;

    public Duck() {
        duckX = 200;
        duckY = 150;
        duckPoint = new Point((int) duckX, (int) duckY);

        try {
            duckImage = ImageIO.read(getClass().getResource("/duck3.png"));

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
            System.err.println("ducky dead :( no quakking");
        }
    }

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
            g2d.drawImage(duckImage, - duckWidth / 2, - duckHeight / 2, duckWidth, duckHeight, null);
        } else {
            g2d.drawImage(flippedDuckImage, - duckWidth / 2, - duckHeight / 2, duckWidth, duckHeight, null);
        }

        g2d.dispose();
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
        Point minimalPoint = null;

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
            if (logPoint.x < duckX + duckWidth / 2
                && logPoint.x + 110 > duckX - duckWidth / 2
                && logPoint.y < duckY + duckHeight / 2
                && logPoint.y + 25 > duckY - duckHeight / 2) {
                    System.out.println("Game over digga");
                    gameOver = true;
                }
        }
    }
    
    public boolean getGameOver() {
        return gameOver;
    }

    public void update(Bread bread, Logs logs) {
        rotateDuck(bread);
        duckPoint.setLocation((int) duckX, (int) duckY);

        checkBreadCollision(bread);
        checkLogsCollision(logs);

        if (duckY - duckHeight / 2 >= 700) {
            gameOver = true;
        }
    }
}