import javax.swing.*;

/**
 * Main
 * Initializes the game window and components.
 */
public class Main {
    // Create the game window and add the game panel
    void update() {
        JFrame frame = new JFrame ("Scaup Ducks Log");
        GamePanel gamePanel = new GamePanel();
        int windowWidth = 500;
        int windowHeight = 700;
        Bread bread = new Bread();
        Duck duck = new Duck(windowWidth, windowHeight);
        Logs logs = new Logs(windowWidth, windowHeight);

        bread.listen(gamePanel);
        gamePanel.setBread(bread);
        gamePanel.setLogs(logs);

        gamePanel.setDuck(duck);

        frame.add(gamePanel);
        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main().update();
    }
}
