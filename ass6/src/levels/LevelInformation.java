package levels;
import gameplay.Block;
import geometry.Velocity;
import logic.interfaces.Sprite;

import java.util.ArrayList;

/**
 * Interface for level information.
 */
public interface LevelInformation {
    /**
     * Number of balls in the game.
     *
     * @return - number of balls to set.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * initialBallVelocities().size() == numberOfBalls().
     *
     * @return - array of velocity for the balls.
     */
    ArrayList<Velocity> initialBallVelocities();

    /**
     * Set speed for paddle.
     *
     * @return int - speed of the paddle.
     */
    int paddleSpeed();

    /**
     * Set width for paddle.
     *
     * @return - width of the paddle.
     */
    int paddleWidth();

    /**
     * Level name, the level name will be displayed at the top of the screen.
     *
     * @return string contain level name.
     */
    String levelName();
    //

    /**
     * Background sprite.
     *
     * @return - sprite with the background of the level
     */
    Sprite getBackground();


    /**
     * Block list.
     *
     * @return - The Blocks that make up this level.
     * Each block contains its size, color and location.
     */
    ArrayList<Block> blocks();

    /**
     * Number of block to consider the game cleared(<= blocks.size()).
     *
     * @return - Number of levels that should be removed.
     */
    int numberOfBlocksToRemove();


    /**
     * Get death block(kill the ball).
     *
     * @return - array of death blocks.
     */
    ArrayList<Block> getDeathBlock();

    /**
     * Return gui width.
     * @return - return gui width.
     */
    int getGuiWidth();
    /**
     * Return gui height.
     * @return - return gui height.
     */
    int getGuiHeight();

    /**
     * Return array of blocks(of the border).
     * @return - array of border blocks.
     */
    ArrayList<Block> getBorderBlocks();

    /**
     * Border Thickness.
     * @return - Border thickness.
     */
    int getBorderThickness();
}
