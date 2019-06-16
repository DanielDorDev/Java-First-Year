package gameplay;
import logic.GameLevel;
import logic.Counter;
import logic.interfaces.Collidable;
import logic.interfaces.HitListener;
import logic.interfaces.HitNotifier;
import logic.interfaces.Sprite;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Line;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for Block creation, implement with Collidable.
 * @author Daniel dor.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    /**
     * Hit listener for the block.
     */
    private ArrayList<HitListener> hitListeners = new ArrayList<>();

    /**
     * Hits counter.
     */
    private Counter countHits;
    /**
     * gameplay.Block is made of the rectangle.
     */
    private Rectangle block;

    /**
     * Control the color of the block.
     */
    private Map<Integer, Color> blockColor = new HashMap<>();

    /**
     * Image for the block.
     */
    private Map<Integer, Image> blockImage = new HashMap<>();

    /**
     * Stroke color.
     */
    private Color stroke;

    /**
     * If the block is a border block(non breakable).
     */
    private boolean notBorder = true;

    /**
     * Set the block data.
     *
     * @param start    - starting point(upper left corner).
     * @param widthX   - width of the block.
     * @param heightY  - height of the block.
     */
    public Block(Point start, double widthX, double heightY) {
        this.block = new Rectangle(start, widthX, heightY);
    }

    /**
     * Set color for the block.
     * @param addInt - integer for fill - k.
     * @param addColor - color of the block.
     */
    public void setBlockColor(Integer addInt, Color addColor) {
        this.blockColor.put(addInt, addColor);
    }

    /**
     * Set image for the block.
     * @param addInt - integer for fill-k.
     * @param addImage - image of the block.
     */
    public void setBlockImage(Integer addInt, Image addImage) {
        this.blockImage.put(addInt, addImage);
    }
    /**
     * Check for the hit collision geometry.Point and change it.
     * Use it only if collision happen (does not check for one)
     * check for the change needed by what line in rectangle got hit.
     * If hit has been made, notify hit all.
     *
     * @param collisionPoint  - point of collision.
     * @param currentVelocity - the current velocity of the collidable class.
     * @param hitter - ball that made the hit.
     * @return - new geometry.Velocity, change dx,dy if needed.
     */
    public Velocity hit(Ball hitter, Point collisionPoint
            , Velocity currentVelocity) {


            // Call a line and check if point is on him.
        // I prefer write it as member for easy to read.
        Line left, right; // upper, floor;
        left = block.getLine("Left");
        right = block.getLine("Right");


        double dx = currentVelocity.getDX(), dy = currentVelocity.getDY();

        if ((left.checkPoint(left, collisionPoint) // Hit left or right line.
                || (right.checkPoint(right, collisionPoint)))) {

            currentVelocity.changeVec(-1 * dx, 1 * dy);

        } else { // For upper or floor lines.

            currentVelocity.changeVec(1 * dx, -1 * dy);

        }
        // If not a border block(non breakable).
        if (notBorder) {
            this.countHits.decrease(1);
        }
        this.notifyHit(hitter);

        // Return the new velocity if changed, otherwise the old one.
        return currentVelocity;
    }

    /**
     * Add stroke(warp color).
     * @param addStroke - stroke color.
     */
    public void setStroke(Color addStroke) {
        this.stroke = addStroke;
    }

    /**
     * Set if the block is border or not(if summon it mean not border).
     */
    public void setNotBorder() {
        this.notBorder = false;
    }

    /**
     * Print the block.
     *
     * @param surface - surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        //Sleeper sleeper = new Sleeper();
       // sleeper.sleepFor(11);
        if (blockColor.isEmpty() && blockImage.isEmpty()) {
            System.err.println("No color for the block");
        }

        if (blockColor.containsKey(countHits.getValue())) {
            if (this.blockColor.get(countHits.getValue()) == null) {

                System.out.print("LOL");
            }
            surface.setColor(this.blockColor.get(countHits.getValue()));
            surface.fillRectangle((int) this.block.getUpperLeft().getX() // x-P.
                    , (int) this.block.getUpperLeft().getY() // y-P(point).
                    , (int) this.block.getWidth() // width.
                    , (int) this.block.getHeight()); // height.
        } else if (blockImage.containsKey(countHits.getValue())) {
            surface.drawImage((int) this.block.getUpperLeft().getX(),
                    (int) this.block.getUpperLeft().getY()
                    , this.blockImage.get(countHits.getValue()));
        } else {
            System.err.println("Missing color for value" + block.getUpperLeft(
            ).getX() + "," + block.getUpperLeft().getY()); // If no color found.
        }
        if (stroke != null) {
            surface.setColor(stroke);
            surface.drawRectangle((int) this.block.getUpperLeft().getX() // x-P.
                    , (int) this.block.getUpperLeft().getY() // y-P(point).
                    , (int) this.block.getWidth() // width.
                    , (int) this.block.getHeight()); // height.);
        }
    }
    /**
     * Notify the sprite that time has passed.
     * @param dt - time passed.
     */
    public void timePassed(double dt) {
    }

    /**
     * Return the rectangle that build the block.
     *
     * @return - return type rectangle object.
     */

    public Rectangle getCollisionRectangle() {
        return this.block;

    }

    /**
     * Set block ("move it").
     * @param blockMove - new rectangle (represent the block).
     */
    public void setBlock(Rectangle blockMove) {
        this.block = blockMove;
    }

    /**
     * Add block to the game.
     * @param g - game to add the current block.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove block from the game(as sprite and collidable).
     * @param gameLevel - game to remove from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl - hit listener to add.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl - hit listener to remove.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);

    }

    /**
     * Notify all hit objects about a hit.
     * @param hitter - ball made the hit.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Return hit points.
     * @return - value of hit points.
     */
    public int getHitPoints() {
        return countHits.getValue();
    }

    /**
     * Set value of count hits.
     * @param value - value of the counter to set.
     */
    public void setCountHits(int value) {
        this.countHits = new Counter(value);
    }

}