package animation;
import biuoop.DrawSurface;
import geometry.Point;
import logic.Counter;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * End screen(when player lose or win).
 */
public class EndScreen implements Animation {

    private Counter scoreCount; // Score count.
    private final int font = 60; // Font size.
    private int imageX = 0; // Image start X axis
    private Image imageBoardA, imageBoardB; // Images of the end screen.
    private boolean moveRight = true; // Move left or right.
    private boolean didPlayerWin; // Player win = true, lose = false.

    /**
     * Construct the end screen.
     * @param didWon    - If player won or lost.
     * @param score    - score of the game.
     */
    public EndScreen(Counter score, boolean didWon) {
        scoreCount = score;
        this.didPlayerWin = didWon;
        setImageIcon(); // Little surprise.
    }

    /**
     * If the player win, this massage will be showed.
     *
     * @param d - draw surface.
     */
    public void playWin(DrawSurface d) {
        new TextFill().printText(d, "You Win! ", font, Color.GREEN
                , Color.GREEN.darker()
                , new Point(d.getWidth() / 4
                        , d.getHeight() / 4));
    }

    /**
     * If the player lose, this massage will be showed.
     *
     * @param d - draw surface.
     */
    public void playLose(DrawSurface d) {
        new TextFill().printText(d, "Game Over. ", font, Color.red
                , Color.red.darker()
                , new Point(d.getWidth() / 4
                        , d.getHeight() / 4 + 2));
    }

    /**
     * Make a frame.
     *
     * @param d - surface that frame will be cast on.
     * @param dt - time passed.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        final int imageSize = 250;
        final int imageMoveSpeed = 3;

        // print the massages(score, space exit, floating image).
        d.setColor(Color.black);
        d.fillRectangle(0, 0
                , d.getWidth()
                , d.getHeight());
        d.setColor(Color.white);

        new TextFill().printText(d, "Your score is:   "
                + scoreCount.getValue().toString(), font / 2, Color.white
                , Color.gray , new Point(d.getWidth() / 4
                        , d.getHeight() / 3));

        d.setColor(Color.white);
        d.drawText(d.getWidth() / 4, d.getHeight() - font
                , "Press Space to Exit", font / 2);

        // If player win or lose (massages).
        if (didPlayerWin) {
            playWin(d);
        } else {
            playLose(d);
        }
        // If image exist.
        if (imageBoardA != null && imageBoardB != null) {
            if (moveRight) {
                d.drawImage(imageX, d.getHeight() / 3 + font, imageBoardA);

                imageX += imageMoveSpeed; // Move.

                if (imageX > d.getWidth()) { // If got to the end width.
                    this.moveRight = false; // Change direction.
                }

            } else {
                d.drawImage(imageX, d.getHeight() / 3 + font, imageBoardB);

                imageX -= imageMoveSpeed; // Move.

                if (imageX < -imageSize) { // If got to the start of the width.
                    this.moveRight = true; // Change direction.
                }
            }
        }
    }

    /**
     * Stop the frame.
     *
     * @return - true or false(on\off).
     */
    public boolean shouldStop() {
        return true;
    }

    /**
     * Set floating picture.
     */
    public void setImageIcon() {
        // Set image , read it from the folder and print it.
        try {
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream(
                    "game_images/gokuFlyV.png");
            this.imageBoardA = ImageIO.read(is);
            is.close();
            InputStream isTwo = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream(
                    "game_images/gokuFly.png");
            this.imageBoardB = ImageIO.read(isTwo);
            isTwo.close();
        } catch (IOException e) {
            System.out.print("Can't find picture");
        }
    }
}
