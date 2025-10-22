import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Duck {
    private final int duckSize = 100;
    private final Point duckPoint;
    private double duckX;
    private double duckY;
    private BufferedImage duckImage;
    private double movementAngle = 0;
    private double rotationAngle = 0;
    int duckWidth;
    int duckHeight;

    public Duck() {
        duckX = 200;
        duckY = 150;
        duckPoint = new Point((int) duckX, (int) duckY);

        try {
            duckImage = ImageIO.read(getClass().getResource("/duck2.png"));

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
        g2d.drawImage(duckImage, - duckWidth / 2, - duckHeight / 2, duckWidth, duckHeight, null);

        g2d.dispose();
    }

    public void rotateDuck(Bread bread) {
        LinkedList<Point> breadPoints = bread.getBreadPoints();
        Point minimalPoint = null;
        double minimalDistance = Double.MAX_VALUE;

        duckY += 1;

        if (breadPoints == null | breadPoints.isEmpty()) {
            return;
        }

        for (Point breadPoint : breadPoints) {
            double distance = Math.sqrt(Math.pow((breadPoint.x - duckPoint.x), 2) + Math.pow((breadPoint.y - duckPoint.y), 2));
            if (distance < minimalDistance) {
                minimalDistance = distance;
                minimalPoint = breadPoint;
            }
        }

        // rotate duck towards minimalPoint
        movementAngle = Math.atan2(minimalPoint.y - duckPoint.y, minimalPoint.x - duckPoint.x);
        rotationAngle = movementAngle + Math.PI / 2;

        duckX += Math.cos(movementAngle) * 2;
        duckY += Math.sin(movementAngle) * 2;
    }

    public void checkCollision(Bread bread) {
        LinkedList<Point> breadPoints = bread.getBreadPoints();
        Point removePoint = null;

        for (Point breadPoint : breadPoints) {
            if (breadPoint.x < duckX + duckWidth / 2
                && breadPoint.x > duckX - duckWidth / 2
                && breadPoint.y < duckY + duckWidth / 2
                && breadPoint.y > duckY - duckWidth / 2) {
                    removePoint = breadPoint;
                }
        }

        if (removePoint != null) {
            breadPoints.remove(removePoint);
        }
    }

    public void update(Bread bread) {
        rotateDuck(bread);
        duckPoint.setLocation((int) duckX, (int) duckY);
        checkCollision(bread);
    }
}