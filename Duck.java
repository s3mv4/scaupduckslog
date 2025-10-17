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

    public Duck() {
        duckX = 200;
        duckY = 150;
        duckPoint = new Point((int) duckX, (int) duckY);

        try {
            duckImage = ImageIO.read(getClass().getResource("/duck.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ducky dead :( no quakking");
        }
    }

    public void draw(Graphics g) {
        if (duckImage == null) {
            return;
        }

        int originalWidth = duckImage.getWidth();
        int originalHeight = duckImage.getHeight();
        int newWidth;
        int newHeight;

        double aspectRatio = originalWidth / originalHeight;

        Graphics2D g2d = (Graphics2D) g.create();

        if (aspectRatio < 1.0) {
            newHeight = duckSize;
            newWidth = (int) (duckSize * aspectRatio);
        } else {
            newWidth = duckSize;
            newHeight = (int) (duckSize / aspectRatio);
        }

        // Translate to the center of the duck
        g2d.translate(duckPoint.x, duckPoint.y);

        // Apply rotation (rotates around origin which is now duck center)
        g2d.rotate(rotationAngle);

        // Duck already translated so no need for duckPoint.x and duckPoint.y
        g2d.drawImage(duckImage, - newWidth / 2, - newHeight / 2, newWidth, newHeight, null);

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

        duckX += Math.cos(movementAngle) * 1;
        duckY += Math.sin(movementAngle) * 2;
    }

    public void update(Bread bread) {
        rotateDuck(bread);
        duckPoint.setLocation((int) duckX, (int) duckY);
    }
}