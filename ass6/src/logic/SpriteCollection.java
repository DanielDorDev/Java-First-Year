package logic;

import biuoop.DrawSurface;
import logic.interfaces.Sprite;

import java.util.ArrayList;

/**
 * Control tbe sprite objects.
 * @author Daniel dor.
 */
public class SpriteCollection {

    private ArrayList<Sprite> spriteArray = new ArrayList<>();


    /**
     * Construct array sprite.
     *
     * @param arrayS - array of type sprite.
     */
    public SpriteCollection(ArrayList<Sprite> arrayS) {
        if (arrayS != null) {
            this.spriteArray.addAll(arrayS);
        }
    }

    /**
     * Add sprite object.
     *
     * @param s - object from type sprite.
     */
    public void addSprite(Sprite s) {
        this.spriteArray.add(s);
    }


    /**
     * Call timePassed() on all sprites.
     * @param dt - how much time have passed.
     */
    public void notifyAllTimePassed(double dt) {
        for (Sprite convert : this.spriteArray) {
            convert.timePassed(dt);
        }
    }

    /**
     * Draw all the sprite objects on the surface.
     *
     * @param d - surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {

        for (Sprite printIt : spriteArray) {
            printIt.drawOn(d);

        }
    }

    /**
     * Return array list of type sprite classes.
     * @return - array list of sprite.
     */
    public ArrayList<Sprite> getSpriteArray() {
        return spriteArray;
    }
}
