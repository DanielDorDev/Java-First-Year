package logic;
import gameplay.Ball;
import gameplay.Block;
import logic.interfaces.HitListener;

/**
 * BlockRemover is in charge of removing blocks from the gameLevel.
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    /**
     * GameLevel to control.
     */
    private GameLevel gameLevel;
    /**
     * Remaining blocks count.
     */
    private Counter remainingBlocks;

    /**
     * Constrctor for the block remover.
     *
     * @param gameLevelObj  - gameLevel to charge his block removing.
     * @param removedBlocks - block number count.
     */
    public BlockRemover(GameLevel gameLevelObj, Counter removedBlocks) {
        this.gameLevel = gameLevelObj;
        this.remainingBlocks = removedBlocks;
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
        if (beingHit.getHitPoints() == 0) { // If block reach 0 hit points.
            beingHit.removeFromGame(this.gameLevel);
            this.remainingBlocks.decrease(1); // Decrease his points.
        }
        this.gameLevel.setBlockCount(this.remainingBlocks);
        // Set new block count.
    }
}
