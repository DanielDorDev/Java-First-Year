package logic;

import biuoop.DrawSurface;
import logic.interfaces.Sprite;

import java.awt.Color;

/**
 * Score indicator(print the score and follow it).
 */
public class ScoreIndicator implements Sprite {

    /**
     * Score value.
     */
    private Counter score;
    /**
     * Test print color.
     */
    private Color color;
    /**
     * Construct score show(give value and color).
     * @param value - of the score.
     * @param whatColor - color of the text print.
     */
    public ScoreIndicator(Counter value, Color whatColor) {
        this.score = value;
        this.color = whatColor;
    }
    /**
     * draw on function for the object.
     *
     * @param d - what surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        final int fontSize = 16;
        d.setColor(this.color);
        d.drawText(d.getWidth() / 3 , fontSize
                , "Score:     " + this.score.getValue().toString(), fontSize);
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
}
