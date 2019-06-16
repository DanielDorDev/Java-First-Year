package levels;

import biuoop.DrawSurface;
import gameplay.Block;
import geometry.Velocity;
import logic.GameLevel;
import logic.interfaces.Sprite;

import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static levels.BlocksDefinitionReader.fromReader;

/**
 * I used the base from Ass5, to construct a pattern of txt reading.
 */
public class LevelFromText extends MapBasic implements
        LevelInformation, Sprite {
    private ArrayList<Velocity> ballVelocityArray; // Ball velocity.
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Image image; // Level background.
    private Color backGroundColor; // Color background.
    private int blockNumber; // How much blocks.
    private int blocksStartX; // X start.
    private int blocksStartY; // Y start.
    private int rowHeight; // row height jumping(how much to move y+row).
    private ArrayList<String> blockLayout; // Block information(how to put).
    private BlocksFromSymbolsFactory factorySymbol; // Factory creator.
    /**
     * Construct level that read from txt.
     * I made it that way so in the future i could add more details and more
     * comfort to change and test it.
     * @param levelText - text of entire level ( a map).
     * @throws Exception - if failed throws from the inside.
     */
    public LevelFromText(ArrayList<String> levelText) throws Exception  {

        constructLevelData(levelText); // Construct level data.
        constructBlockData(levelText); // Construct block data.
        createBlocksWithLayout();      // Create blocks with layout.
    }

    /**
     * Create block factory uses reading function.
     * @param levelText -information from the path about block factory.
     */
    public void constructBlockData(ArrayList<String> levelText) {
        try { // Read from level text create a path file and with from reader.
            // Create block factory symbol.
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream(new LevelReader(
            ).blockDefReader(levelText));

            this.factorySymbol = fromReader(new InputStreamReader(is));

        } catch (Exception e)  {
            System.err.println("Failed open construct block data");
        }
    }

    /**
     * Create block with layout given(uses factory block).
     */
    public void createBlocksWithLayout() {

        int moveX = blocksStartX; // How much move x.
        int moveY = blocksStartY; // how much move y.
        for (String line : this.blockLayout) { // Go on the block layout.
            // Create a array to go by 1 letter string.
            // Asked with string (i would prefer char).
            String[] check = line.split("");

            // Go by all chars and check for spacer\block and create it.
            for (int i = 0; i < line.length(); i++) {
                if (factorySymbol.isSpaceSymbol(check[i])) {
                    // Move the x (as block go right).
                    moveX += factorySymbol.getSpaceWidth(check[i]);
                } else if (factorySymbol.isBlockSymbol(check[i])) {
                    Block block = factorySymbol.getBlock(
                            check[i], moveX, moveY);
                    this.getGameBlockList().add(block);
                    // Move the x (as block go right).
                    moveX += block.getCollisionRectangle().getWidth();

                } else { // Missing layout.
                    System.err.println("Layout symbol does not exist"
                            + check[i]);
                }
            } // Move x back to original place(start new line).
            moveX = blocksStartX;
            moveY += this.rowHeight; // Move row in new line.
        }
    }

    /**
     * Construct level data, create it using a level reader.
     * I choose to use it because it easier to change or add.
     * And i don't like using static functions.
     * @param levelText - text to read from.
     * @throws Exception - if failed in reading(print it and trace it).
     */
    public void constructLevelData(ArrayList<String> levelText)
            throws Exception {
        // Create all the level info.
        this.paddleSpeed = new LevelReader().paddleSpeedReader(levelText);
        this.paddleWidth = new LevelReader().paddleWidthReader(levelText);
        this.levelName = new LevelReader().readLevelName(levelText);
        this.blockNumber = new LevelReader().blockNumberReader(levelText);
        this.blocksStartX = new LevelReader().blockStartXReader(levelText);
        this.blocksStartY = new LevelReader().blockStartYReader(levelText);
        this.rowHeight = new LevelReader().rowHighReader(levelText);
        this.ballVelocityArray = new LevelReader().readBallVelocity(levelText);
        this.blockLayout = new LevelReader().blockLayOut(levelText);
        this.image = new LevelReader().imageReader(levelText);
        if (this.image == null) { // If we cant find image
            this.backGroundColor = new LevelReader().colorReader(levelText);
        }
    }

    /**
     * The initial velocity of each ball.
     * initialBallVelocities().size() == numberOfBalls().
     *
     * @return - array of velocity for the balls.
     */
    @Override
    public ArrayList<Velocity> initialBallVelocities() {
        return ballVelocityArray;
    }
    /**
     * Background sprite.
     *
     * @return - sprite with the background of the level
     */
    @Override
    public Sprite getBackground() {
            return this;
    }

    /**
     * Number of balls in the game.
     *
     * @return - number of balls to set.
     */
    @Override
    public int numberOfBalls() {
        return ballVelocityArray.size();
    }



    /**
     * Set speed for paddle.
     *
     * @return int - speed of the paddle.
     */
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Set width for paddle.
     *
     * @return - width of the paddle.
     */
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }

    /**
     * Level name, the level name will be displayed at the top of the screen.
     *
     * @return string contain level name.
     */
    @Override
    public String levelName() {
        return levelName;
    }



    /**
     * Block list.
     *
     * @return - The Blocks that make up this level.
     * Each block contains its size, color and location.
     */
    @Override
    public ArrayList<Block> blocks() {
        return getGameBlockList();
    }

    /**
     * Number of block to consider the game cleared(<= blocks.size()).
     *
     * @return - Number of levels that should be removed.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blockNumber;
    }

    /**
     * Get death block(kill the ball).
     *
     * @return - array of death blocks.
     */
    @Override
    public ArrayList<Block> getDeathBlock() {
        ArrayList<Block> deathArray = new ArrayList<>();
        deathArray.add(floorDeathBlock());
        return deathArray;
    }
    /**
     * draw on function for the object.
     *
     * @param d - what surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {

        if (image != null) {
            d.drawImage(0, 0, this.image);
        } else {
            d.setColor(this.backGroundColor);
            d.fillRectangle(0 , 0, getGuiWidth(), getGuiHeight());
        }
        this.drawMapName(d, levelName());

    }

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt - time passed.
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * Add object to the game.
     *
     * @param g - game to add the current object.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
