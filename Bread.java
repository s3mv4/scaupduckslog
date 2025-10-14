import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Bread extends JPanel implements MouseListener {
    private final LinkedList<Point> breadPoints = new LinkedList<>();
    private final int breadSize = 10;

    public Bread() {
        setBackground(new Color(0,0,0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(139, 69, 19));
        for (Point breadPoint : breadPoints) {
            g.fillRect(breadPoint.x - breadSize/2, 
                breadPoint.y - breadSize/2, 
                breadSize, 
                breadSize);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickedAt = e.getPoint();
        if (breadPoints.size() == 5) {
            breadPoints.removeFirst();
        }
        breadPoints.add(clickedAt);
        repaint();
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
