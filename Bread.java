import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Bread implements MouseListener {
    private final LinkedList<Point> breadPoints = new LinkedList<>();
    private final int breadSize = 20;
    private BufferedImage breadImage;

    public Bread() {
        try {
            breadImage = ImageIO.read(getClass().getResource("/images/bread.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("bread eaten ;P (bread.png not found)");
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        for (Point breadPoint : breadPoints) {
            g.drawImage(breadImage, breadPoint.x, breadPoint.y, breadSize, breadSize, null);
        }
    }

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

    public void reset() {
        breadPoints.clear();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickedAt = e.getPoint();
        if (breadPoints.size() == 5) {
            breadPoints.removeFirst();
        }
        breadPoints.add(clickedAt);
        e.getComponent().repaint();
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
