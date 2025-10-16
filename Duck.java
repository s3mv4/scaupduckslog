import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Duck {
    private final int duckSize = 100;
    private final Point duckPoint;
    private BufferedImage duckImage;

    public Duck() {
        duckPoint = new Point(100, 100);

        try {
            duckImage = ImageIO.read(getClass().getResource("/duck.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ducky dead :( no quakking");
        }
    }

    public void draw(Graphics g) {
        if (duckImage != null) {
            int originalWidth = duckImage.getWidth();
            int originalHeight = duckImage.getHeight();
            int newWidth;
            int newHeight;

            double aspectRatio = originalWidth / originalHeight;

            if (aspectRatio < 1.0) {
                newHeight = duckSize;
                newWidth = (int) (duckSize * aspectRatio);
            } else {
                newWidth = duckSize;
                newHeight = (int) (duckSize / aspectRatio);
            }

            g.drawImage(duckImage,
                duckPoint.x - duckImage.getWidth() / 2,
                duckPoint.y - duckImage.getHeight() / 2, 
                newWidth,
                newHeight,
                null);
        }
    }

    public void update() {
    }
}