import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

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

    int logWidth = 95;
    int logHeight = 29;

    int logX = 0;
    int logY = logHeight;
    ArrayList<Block> logArray;

    // movement specifics

    int gravity = 1;
    int velocityX = 12;

    Timer enterLogTimer;

    public void Logs() {

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

    public void enterLog() {
        private static final int cols = 5;
        private static final int rows = 1;
        final double chance = 0.4;// 40% for the image to occur

        Image[] grid = new Image[cols];
        Random random = new Random();

        for (int i = 0; i < cols; i++){
            if (random.nextDouble() < chance){
                grid[i] = logImg;
            } 
            else{
                grid[i] = null;
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

     public void draw(Graphics g) {

        g.drawImage()

     }


   
}
