import javax.swing.*;

public class Main {
    void update() {
        JFrame frame = new JFrame ("Scaup Ducks Log");
        GamePanel gamePanel = new GamePanel();
        Bread bread = new Bread();
        Logs logs = new Logs();

        bread.listen(gamePanel);
        gamePanel.setBread(bread);
        gamePanel.setLogs(logs);

        frame.add(gamePanel);
        frame.setSize(500, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main().update();
    }
}
