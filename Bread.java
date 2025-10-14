import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Bread implements MouseListener {
    private final LinkedList<Point> breadPoints = new LinkedList<>();
    private final int breadSize = 10;

    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        for (Point breadPoint : breadPoints) {
            g.fillRect(breadPoint.x - breadSize/2, 
                breadPoint.y - breadSize/2, 
                breadSize, 
                breadSize);
        }
    }

    public void update() {
        for (Point breadPoint : breadPoints) {
            breadPoint.y += 2;
        }
    }

    public void listen(JPanel gamePanel) {
        gamePanel.addMouseListener(this);
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
