package levels;
import java.util.Random;

import biuoop.DrawSurface;
import gameplay.Block;
import geometry.Point;
import geometry.Velocity;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Basic map information.
 */
abstract class MapBasic {
    /**
     * Gui width\height, sizeBorderWidth(how much the border thickness).
     */
    private static final int GAME_WIDTH = 800; // Game width.
    private static final int GAME_HEIGHT = 600; // Game high.
    private static final int BORDER_THICKNESS = 25;

    private ArrayList<Block> borderBlocks = new ArrayList<>();

    private ArrayList<Block> gameBlockList = new ArrayList<>();

    private Block floorDeathBlock;

    /**
     * Set stage basic info, build borders.
     * Main function for setting the game.
     */
    public MapBasic() {

        final Color colorBorder = Color.getHSBColor(143, 155, 255);

        this.borderBlocks.add(new Block(new Point(BORDER_THICKNESS
                , BORDER_THICKNESS), GAME_WIDTH - BORDER_THICKNESS
                , BORDER_THICKNESS)); // Upper.


        this.borderBlocks.add(new Block(new Point(0, BORDER_THICKNESS),
                BORDER_THICKNESS, GAME_HEIGHT)); // Left.

        this.borderBlocks.add(new Block(new Point(GAME_WIDTH
                - BORDER_THICKNESS, BORDER_THICKNESS), BORDER_THICKNESS,
                GAME_HEIGHT)); // Right.

        floorDeathBlock = new Block(new Point(BORDER_THICKNESS
                , GAME_HEIGHT - BORDER_THICKNESS / 5)
                , GAME_WIDTH - BORDER_THICKNESS * 2
                , BORDER_THICKNESS / 5); // Floor
        floorDeathBlock.setNotBorder();
        floorDeathBlock.setCountHits(1);
        floorDeathBlock.setBlockColor(1, colorBorder);

        for (Block colorChange : borderBlocks) {
            colorChange.setNotBorder();
            colorChange.setCountHits(1);
            colorChange.setBlockColor(1, colorBorder);

        }
    }

    /**
     * Return height of gui.
     *
     * @return int - height of gui.
     */
    public int getGuiHeight() {
        return GAME_HEIGHT;
    }

    /**
     * Return width of gui.
     *
     * @return int - width of gui.
     */
    public int getGuiWidth() {
        return GAME_WIDTH;
    }

    /**
     * Return border thickness.
     *
     * @return - int represent border thickness.
     */
    public int getBorderThickness() {
        return BORDER_THICKNESS;
    }

    /**
     * Return list of border blocks.
     *
     * @return - array of border blocks.
     */
    public ArrayList<Block> getBorderBlocks() {
        return borderBlocks;
    }

    /**
     * Get death block(kill the ball).
     *
     * @return - floor death block.
     */
    protected Block floorDeathBlock() {
        return floorDeathBlock;
    }

    /**
     * Draw map name.
     *
     * @param d         - surface to draw on.
     * @param levelName - map name.
     */
    protected void drawMapName(DrawSurface d, String levelName) {
        final int fontSize = 16; // font size.
        final String msg = "Level Name: ";
        d.setColor(Color.gray.darker());
        d.fillRectangle(0, 0, getGuiWidth(), getBorderThickness());
        d.setColor(Color.white);
        d.drawText(getGuiWidth() - (fontSize / 2) * (msg.length()
                        + levelName.length()) - 10, fontSize
                , msg + levelName, fontSize);
    }

    /**
     * Set random angle for the ball ( a default option).
     *
     * @return - a random velocity.
     */
    public Velocity setRandSpeedAngle() {
        Random rand = new Random();

        // Angle limit for the random & starting speed of the ball.
        final int angleLimit = 120, speedStart = 300;
        int angle = 60 - rand.nextInt(angleLimit);

        return Velocity.fromAngleAndSpeed(angle, speedStart);
    }

    /**
     * Return list of game blocks.
     *
     * @return - array of Block list.
     */
    public ArrayList<Block> getGameBlockList() {
        return gameBlockList;
    }
}
