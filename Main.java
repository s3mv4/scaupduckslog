import javax.swing.*;

public class Main {
    void update() {
        JFrame frame = new JFrame ("Scaup Ducks Log");
        GamePanel gamePanel = new GamePanel();
        Bread bread = new Bread();
        Duck duck = new Duck();
        Logs logs = new Logs();

        bread.listen(gamePanel);
        gamePanel.setBread(bread);
        gamePanel.setLogs(logs);

        gamePanel.setDuck(duck);

        frame.add(gamePanel);
        frame.setSize(500, 700);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main().update();
    }
}
