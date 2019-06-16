package logic.interfaces;
import gameplay.Block;
import gameplay.Ball;

/**
 * Hit listener class, used when hit happen.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the block that got hit.
     * @param hitter - the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}