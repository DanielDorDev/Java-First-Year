package logic.interfaces;

import biuoop.DrawSurface;
import logic.GameLevel;

/**
 * Spire interface for objects.
 * @author Daniel dor.
 */
public interface Sprite {

    /**
     * draw on function for the object.
     *
     * @param d - what surface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     * @param dt - time passed.
     */
    void timePassed(double dt);

    /**
     * Add object to the game.
     *
     * @param g - game to add the current object.
     */
    void addToGame(GameLevel g);
}
