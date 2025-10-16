import javax.swing.*;

public class Main {
    void update() {
        JFrame frame = new JFrame ("Scaup Ducks Log");
        GamePanel gamePanel = new GamePanel();
        Bread bread = new Bread();
        Duck duck = new Duck();

        bread.listen(gamePanel);
        gamePanel.setBread(bread);

        gamePanel.setDuck(duck);

        frame.add(gamePanel);
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main().update();
    }
}
