import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bread bread;
    private Duck duck;
    private Logs logs;
    public int frameRate = 60;
    private int logCounter;
    private BufferedImage deadDuckImage;
    private boolean drawDeadDuck = false;
    private float deadDuckAlpha = 0f;
    private float textAlpha = 0f;
    private int score = 0;
    private boolean deathSoundPlayed = false;
    private Clip soundEffect;
    private Clip backgroundMusic;
    private boolean backgroundMusicPlaying = false;
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public GamePanel() {
        setBackground(new Color(15, 50, 180));
        setFocusable(true);

        try {
            deadDuckImage = ImageIO.read(getClass().getResource("/images/duckdead.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ducky will live another day (duckdead.png not found)");
        }

        Timer timer = new Timer(1000/frameRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (duck != null && duck.getGameOver()) {
                    drawDeadDuck = true;

                    if (deathSoundPlayed == false) {
                        backgroundMusic.stop();
                        backgroundMusicPlaying = false;
                        playAudio("/audio/suspense.wav", false);
                        deathSoundPlayed = true;
                    }
                } else {
                    if (backgroundMusicPlaying == false) {
                        playAudio("/audio/Fluffing a Duck.wav", true);
                        backgroundMusicPlaying = true;
                    }

                    if (duck != null) {
                        duck.setBread(bread);
                        duck.setLogs(logs);
                    }

                    for (Sprite sprite : sprites) {
                        sprite.update();
                    }

                    logCounter += 1;
                    if (logCounter == 60) {
                        logCounter = 0;
                        score += 1;
                        logs.addLogs();
                    }
                }

                if (drawDeadDuck && deadDuckAlpha < 1f) {
                    deadDuckAlpha += 0.007f;
                    if (deadDuckAlpha >= 1.0f) {
                        deadDuckAlpha = 1f;
                    }
                } else if (drawDeadDuck && textAlpha < 1f) {
                    textAlpha += 0.007f;
                    if (textAlpha >= 1.0f) {
                        textAlpha = 1f;
                    }
                }

                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    drawDeadDuck = false;
                    deadDuckAlpha = 0f;
                    textAlpha = 0f;
                    score = 0;
                    soundEffect.stop();
                    deathSoundPlayed = false;

                    for (Sprite sprite : sprites) {
                        sprite.reset();
                    }
                }
            }
        });
    }

    public void setBread(Bread bread) {
        this.bread = bread;
        sprites.add(bread);
    }

    public void setDuck(Duck duck) {
        this.duck = duck;
        sprites.add(duck);
    }

    public void setLogs(Logs logs) {
        this.logs = logs;
        sprites.add(logs);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(new Color(7, 25, 50));
        g2d.setFont(new Font("Arial", Font.BOLD, 100));

        String scoreText = Integer.toString(score);

        int scoreTextWidth = g2d.getFontMetrics().stringWidth(scoreText);
        int scoreTextX = getWidth()/2 - scoreTextWidth/2;
        int scoreTextY = getHeight()/2 - 100/2;

        g2d.drawString(scoreText, scoreTextX, scoreTextY);

        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }

        if (drawDeadDuck && deadDuckImage != null) {
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, deadDuckAlpha));
            g2d.drawImage(deadDuckImage, 
                getWidth()/2 - deadDuckImage.getWidth(), 
                getHeight()/2 - deadDuckImage.getHeight() - 75, 
                deadDuckImage.getWidth()*2, 
                deadDuckImage.getHeight()*2, 
                null);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, textAlpha));
            g2d.setColor(new Color(255, 255, 255));

            g2d.setFont(new Font("Arial", Font.BOLD, 50));

            scoreTextWidth = g2d.getFontMetrics().stringWidth(scoreText);
            scoreTextX = getWidth()/2 - scoreTextWidth/2;
            scoreTextY = getHeight() - 100;

            g2d.drawString(scoreText, scoreTextX, scoreTextY);

            g2d.setFont(new Font("Arial", Font.BOLD, 28));

            String text = "PRESS SPACE TO RESTART";

            int textWidth = g2d.getFontMetrics().stringWidth(text);
            int textX = getWidth()/2 - textWidth/2;
            int textY = getHeight() - 50;

            g2d.drawString(text, textX, textY);

            g2d.dispose();
        }
    }

    private void playAudio(String filePath, boolean repeatSound) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filePath));
            if (repeatSound == true) {
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioIn);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusic.start();
            } else {
                soundEffect = AudioSystem.getClip();
                soundEffect.open(audioIn);
                soundEffect.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException e) {
            System.err.println("please (don't) stop the music (" + filePath + " not found)");
        }
    }
}