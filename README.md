# Requirements:
- Java 17 or higher.
- Windows, macOS, or Linux.

# Running
## Option 1
1. Open project folder in VSCode.
2. Navigate to Main.java.
3. Click the 'Run Java' button in the top right.

## Option 2
1. Open terminal in project directory.
2. Compile the game:
```javac *.java```

3. Run the game:
```java Main```

# Testing
| Feature | How to test |
| --- | --- |
| Game launch | The game compiles, starts and displays the game window. |
| Bread Placement | A mouse click on the screen will make a piece of bread appear at the position of the click. |
| Duck Movement | The duck rotates and moves to the nearest piece of bread. |
| Logs Generation | Every second, 1-3 logs spawn at the top of the screen, each log is in 1 of 4 possible positions and they move downward. |
| Duck and Bread Collision | When the duck collides with a piece of bread, the duck stops moving and the piece of bread disappears. |
| Duck and Log Collision | When the duck collides with a log, the game over screen is displayed. |
| Score | The score is displayed on the screen and increases by 1 every second the duck is alive. The score is displayed on the game over screen and resets when the game is restarted. |
| Audio | Background music (Fluffing a Duck.wav) is being played during gameplay. Sound effect (suspense.wav) is played when the game over screen shows up and the background music should stop untill the game is restarted. |
| Game Restart | Game can be restarted by pressing space. The duck should reset to the center and rotate upwards. The score should reset to 0. All bread and logs should be removed from the screen. The background music sholud resume |
| Sprites | All images are displayed correctly. The duck (duck3.png), bread (bread.png), logs (log.png) and the dead duck (deadduck.png) should appear. |
| GUI | Window is not resizable, centered and should close when the window is closed. |