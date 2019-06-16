package animation;

import biuoop.DrawSurface;
import geometry.Point;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class for pausing the game.
 */
public class PauseScreen implements Animation {
    private Image imageBoard;

    /**
     * Set the pause screen.
     */
    public PauseScreen() {
        setImageIcon(); // Set image.
    }

    /**
     * Print the frame.
     *
     * @param dt - time passed.
     * @param d - surface that frame will be cast on.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        // If image is not empty.
        if (imageBoard != null) {
            d.drawImage(0, 0, this.imageBoard);
        }

        d.setColor(Color.MAGENTA.darker());
        d.drawText(10, d.getHeight() / 4 + 200,
                "Hit stopped the game!", 20);


        new TextFill().printText(d, "Press space to continue"
                , 50, Color.magenta, Color.magenta.darker()
                , new Point(10, d.getHeight() / 2));
    }
    /**
     * when the game should stop.
     *
     * @return - stop true or false.
     */
    public boolean shouldStop() {
        return true;
    }


    /**
     * Set Game image.
     */
    public void setImageIcon() {
        // Set image , read it from the folder and print it.
        try {
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream("timeHit.jpeg");

            this.imageBoard = ImageIO.read(is);

        } catch (IOException e) {
            this.imageBoard = null;
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