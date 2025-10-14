import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Bread extends JPanel implements MouseListener {
    private LinkedList<Point> breadPoints = new LinkedList<>();
    private int breadSize = 10;

    public Bread() {
        setBackground(new Color(0,0,0));
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(139, 69, 19));
        for (Point breadPoint : breadPoints) {
            g.fillRect(breadPoint.x, breadPoint.y, breadSize, breadSize);
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
