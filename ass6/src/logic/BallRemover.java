package logic;
import gameplay.Ball;
import gameplay.Block;
import logic.interfaces.HitListener;

/**
 * BlockRemover is in charge of removing blocks from the gameLevel.
 * as well as keeping count of the number of blocks that remain.
 */

public class BallRemover implements HitListener {
    /**
     * GameLevel to control.
     */
    private GameLevel gameLevel;
    /**
     * Remaining blocks count.
     */
    private Counter remainingBalls;

    /**
     * Constrctor for the block remover.
     *
     * @param gameLevelObj     - gameLevel to charge his ball removing.
     * @param removeBallRemain - ball number count.
     */
    public BallRemover(GameLevel gameLevelObj, Counter removeBallRemain) {
        this.gameLevel = gameLevelObj;
        this.remainingBalls = removeBallRemain;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * Block with zero hit points will be removed.
     * Remove the listener too.
     *
     * @param beingHit - the block that got hit.
     * @param hitter   - the Ball that's doing the hitting.
     */

    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(gameLevel);  // Remove as sprite from the game.
        this.gameLevel.removeBall(hitter); // remove ball from array balls.
        this.remainingBalls.decrease(1); // Decrease his points.
        this.gameLevel.setBallCount(this.remainingBalls);
        // Set new block count.
    }
}