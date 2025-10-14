import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Logs extends JPanel {

    int panelWidth = 500;
    int panelHeight = 700;

    //put image of the log
    Image logImg;

    class Block {
        int x;
        int y;
        int blockWidth;
        int blockHeight;
        Image img;

        Block(int x, int y, int blockWidth, int blockHeight, Image img) {
            this.x = x;
            this.y = y;
            this.blockWidth = blockWidth;
            this.blockHeight = blockHeight;
            this.img = img;
    }

    int logWidth;
    int logHeight;

    int logX;
    int logY;
    ArrayList<Block> logArray;

    // movement specifics

    int gravity = 1;

    Timer enterLogTimer;

    public Logs() {

        setPreferredSize(new Dimension( panelWidth, panelHeight));

        logImg = new ImageIcon(getClass().getResource("./image/log.png")).getImage(); 


        logArray = new ArrayList<Block>();

        enterLogTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterLog();
            }
        });
        enterLogTimer.start();
    }
}
