package levels;

import gameplay.Block;

/**
 * Block creator interface.
 */
public interface BlockCreator {
    /**
     * Create block with the specific detail of the class in a location.
     * @param xpos - x axis
     * @param ypos - y axis
     * @return - new block with the new information.
     */
    Block create(int xpos, int ypos);
}