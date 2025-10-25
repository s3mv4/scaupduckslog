import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Bread extends Sprite implements MouseListener {
    private final LinkedList<Point> breadPoints = new LinkedList<>();
    private final int breadSize = 20;
    private final int breadAmount = 3;
    private BufferedImage breadImage;
    private boolean breadReady = true;

    public Bread() {
        try {
            breadImage = ImageIO.read(getClass().getResource("/images/bread.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("bread eaten ;P (bread.png not found)");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        for (Point breadPoint : breadPoints) {
            g.drawImage(breadImage, breadPoint.x, breadPoint.y, breadSize, breadSize, null);
        }
    }

    @Override
    public void update() {
        for (Point breadPoint : breadPoints) {
            breadPoint.y += 3;
        }
    }

    public void listen(JPanel gamePanel) {
        gamePanel.addMouseListener(this);
    }

    public LinkedList<Point> getBreadPoints() {
        return breadPoints;
    }

    public void setBreadReady(boolean ready) {
        breadReady = ready;
    }

    @Override
    public void reset() {
        breadPoints.clear();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (breadReady == false) {
            return;
        }

        Point clickedAt = e.getPoint();
        if (breadPoints.size() == breadAmount) {
            breadPoints.removeFirst();
        }
        breadPoints.add(clickedAt);
        e.getComponent().repaint();
        breadReady = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }
}
