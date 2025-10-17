import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Logs {

    private BufferedImage logImage;

    public void Logs(){

       
        
         try {
            logImage = ImageIO.read(getClass().getResource("/log.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("opa");
        }

    }

     public void draw(Graphics g) {

        if (logImage != null){
         g.drawImage(logImage, 20, 20, null);
        }

      }

      public void update() {

      }

    
}
