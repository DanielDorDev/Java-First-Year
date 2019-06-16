package logic;

import gameplay.Ball;
import gameplay.Block;
import logic.interfaces.HitListener;

/**
 * Keep track of the score.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore; // Score count.

    /**
     * Set score listener(follow the score).
     *
     * @param scoreCounter - counter to follow.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit - the block that got hit.
     * @param hitter   - the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            // If block destroyed he doesn't point.
            this.currentScore.increase(10);
        }
    }
}
