package logic;

import biuoop.DrawSurface;
import logic.interfaces.Sprite;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

/**
 * Print and count lives for the game.
 */
public class LivesIndicator implements Sprite {

    /**
     * Score value.
     */
    private Counter lives;
    /**
     * Test print color.
     */
    private Color color;
    /**
     * Image for the live indicator.
     */
    private Image imageBoard;

    /**
     * Construct score show(give value and color).
     * @param value - of the score.
     * @param whatColor - color of the text print.
     */
    public LivesIndicator(Counter value, Color whatColor) {
        this.lives = value;
        this.color = whatColor;
        setImageIcon();
    }
    /**
     * draw on function for the object.
     *
     * @param d - what surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        final int fontSize = 16;
        d.setColor(this.color);
        d.drawText(fontSize + 4 , fontSize, "Lives:", fontSize);
        if (imageBoard != null) {
            for (int i = 0; i < this.lives.getValue(); i++) {
                d.drawImage(65 + i * 22, 0, imageBoard);
            }
        } else {
            d.drawText(65, 0, this.lives.getValue().toString(), fontSize);
        }

    }

    /**
     * Notify the sprite that time has passed.
     * @param dt - time passed.
     */
    public void timePassed(double dt) {

    }

    /**
     * Add object to the game.
     *
     * @param g - game to add the current object.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    /**
     * Set image icon(beans as lives).
     */
    public void setImageIcon() {
        // Set image , read it from the folder and print it.

        try {
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream("game_images/bean.png");

            this.imageBoard = ImageIO.read(is);
            is.close();

        } catch (IOException e) {
            this.imageBoard = null;
            System.out.print("\nCannot find background, default on.");
        } finally {
            try {
                if (imageBoard != null) {
                    imageBoard.flush();
                }
            } catch (Exception e) {
                System.out.print("Failed to flush");
            }
        }
    }
}
