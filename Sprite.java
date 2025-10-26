import java.awt.*;

/** 
 * Sprite
 * Abstract base class for sprites.
 * Duck, Bread and Logs use this with inheritance.
 */
public abstract class Sprite {
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract void reset();
}