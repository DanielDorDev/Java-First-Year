package animation;
import biuoop.DrawSurface;
import geometry.Point;
import logic.Counter;
import logic.SpriteCollection;

import java.awt.Color;

/**
 * Count down start of turn animation.
 */
public class CountdownAnimation implements Animation {

    private SpriteCollection spriteInfo;
    private boolean running;
    private Counter countNumbers;
    /**
     * Construct the countdown.
     *
     * @param countFrom    - count down from number to zero(go).
     * @param gameScreen   - game to print the massage on.
     */
    public CountdownAnimation(int countFrom,
                              SpriteCollection gameScreen) {

        countNumbers = new Counter(countFrom);
        this.spriteInfo = new SpriteCollection(gameScreen.getSpriteArray());
        this.running = true;
    }

    /**
     * Print frame(animation).
     *
     * @param d - surface that frame will be cast on.
     * @param dt - time passed.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        final int fontSize = 50;


        spriteInfo.drawAllOn(d); // draw the game.

        // Print the rectangle "background".
        d.setColor(Color.white);
        d.fillRectangle((d.getWidth() - fontSize) / 2
                , d.getHeight() / 6 - fontSize
                , fontSize + fontSize / 2, fontSize + fontSize / 7);
        d.setColor(Color.gray);
        d.drawRectangle((d.getWidth() - fontSize) / 2
                , d.getHeight() / 6 - fontSize
                , fontSize + fontSize / 2, fontSize + fontSize / 7);
        if (countNumbers.getValue() == 0) {
            this.running = false;
            //  return;
        }
        // Print the number.
        if (countNumbers.getValue() > 0) {


            new TextFill().printText(d, "  "
                    + (countNumbers.getValue()).toString(), fontSize, Color.red
                    , Color.red.darker()
                    , new Point((d.getWidth() - fontSize) / 2
                    , d.getHeight() / 6));
            countNumbers.decrease(1);

        } else {
            // Print the Go massage.
            new TextFill().printText(d, "Go", fontSize
                    , Color.green, Color.green.darker()
                    , new Point((d.getWidth() - fontSize) / 2 + 3
                            , d.getHeight() / 6));
        }
    }

    /**
     * Stop the frame.
     *
     * @return - true or false(on\off).
     */
    public boolean shouldStop() {
        return !this.running;
    }
}

