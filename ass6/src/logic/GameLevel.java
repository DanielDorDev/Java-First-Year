package logic;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import biuoop.DrawSurface;
import geometry.Point;
import gameplay.Ball;
import gameplay.Block;
import gameplay.Paddle;
import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.PauseScreen;
import animation.KeyPressStoppableAnimation;
import logic.interfaces.Sprite;
import logic.interfaces.Collidable;
import levels.LevelInformation;
import logic.interfaces.HitNotifier;
import javax.imageio.ImageIO;

/**
 * Hold the sprites and the collidable, charge of the animation.
 * @author Daniel dor.
 */
public class GameLevel implements Animation {

    private static final int BALL_SIZE = 6;
    /**
     * Count blocks & balls in the game, scoreCount count, live count.
     */
    private Counter blockCount, ballCount, scoreCount, liveCount;
    /**
     * Main paddle that the player control it.
     */
    private Paddle paddle;

    /**
     * Create new spriteCollection class.
     */
    private SpriteCollection sprites = new SpriteCollection(null);
    /**
     * Create new logic.GameEnvironment class.
     */
    private GameEnvironment environment = new GameEnvironment(null);

    /**
     * Array of the game balls.
     */
    private ArrayList<Ball> balls = new ArrayList<>();

    /**
     * Set stage of the current game.
     */
    private LevelInformation stage;

    /**
     * Runner for the game(run animation).
     */
    private AnimationRunner runner;

    /**
     * Keep run the game = true, otherwise don't run.
     */
    private boolean running;

    /**
     * Key board sensor.
     */
    private biuoop.KeyboardSensor keyboardControl;
    private Image imageBall; // Image of the ball.

    /**
     * Initialize a new game: set stage, create the Blocks, set score.
     * and add them to the game.
     * get them from another map classes for letter use.
     *
     * @param levelInfo - info of the level.
     * @param run       - runner for the game.
     * @param liveNum   - count for lives.
     * @param keyboard  - key board sensor.
     * @param score     - count score for the game.
     */
    public void initialize(LevelInformation levelInfo, AnimationRunner run
            , Counter liveNum, biuoop.KeyboardSensor keyboard, Counter score) {
        liveCount = liveNum;
        stage = levelInfo;
        this.runner = run;
        this.scoreCount = score;
        running = true;
        this.keyboardControl = keyboard;
        setImageIcon();
        addGameProperties();

    }

    /**
     * Set properties for the game.
     */
    public void addGameProperties() {

        setBlockCount(new Counter(stage.numberOfBlocksToRemove()));


        sprites.addSprite(stage.getBackground());
        for (Block block : stage.getBorderBlocks()) {
            block.addToGame(this);
        }
        for (Block block : stage.blocks()) {
            block.addToGame(this);
        }
        for (Block block : stage.getDeathBlock()) {
            block.addToGame(this);
        }
        paddle = getPaddle();

        this.paddle.addToGame(this);

        this.paddle.setKeyBoard(keyboardControl);

        ScoreIndicator startCount = new ScoreIndicator(this.scoreCount
                , Color.white);
        startCount.addToGame(this);
        setBlockListen(stage);

    }

    /**
     * Set game play parts(Paddle place, Balls, listeners).
     */
    private void setGamePlay() {

        if (this.paddle != null) {
            this.paddle.setPaddle(this.paddle.getPaddleCopy());
        }

        setStartBalls(BALL_SIZE);

        setBallCount(new Counter(this.balls.size()));

        setBallListen(stage);

        LivesIndicator live = new LivesIndicator(liveCount, Color.white);

        live.addToGame(this);

    }

    /**
     * Run the game -- Play turn while running allowed, in the end close gui.
     */
    public void run() {

        while (liveCount.getValue() > 0) {

            playOneTurn();
            if (blockCount.getValue() == 0) {
                return;
            } else {
                this.liveCount.decrease(1);
            }
        }
    }

    /**
     * Run starting screen.
     */
    public void runGoScreen() {
        this.running = true;
        final int waitForSeconds = 2, countFromToZero = 3;
        // +1 for the go.
        final double howMuchForObject = (double) (countFromToZero + 1)
                / (double) waitForSeconds;

        AnimationRunner startScreen = new AnimationRunner(this.runner.getGui()
                , howMuchForObject , this.runner.getSleeper());

        startScreen.run(new CountdownAnimation(countFromToZero, sprites));

    }

    /**
     * play the game -- start the animation loop.
     */
    public void playOneTurn() {
        setGamePlay();

        runGoScreen();

        this.running = true;
        this.runner.run(this);
    }

    /**
     * Make a frame(make a move, draw it, notify time pass).
     * Check if loop need to end(is so running will become false).
     *
     * @param dt - time passed.
     * @param d - surface that frame will be cast on.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        //moveAllBalls(dt);
        drawGame(d); // Draw function(draws all).
        this.sprites.notifyAllTimePassed(dt);

        if (this.runner.getGui().getKeyboardSensor().isPressed("p")
                || this.runner.getGui().getKeyboardSensor().isPressed("P")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboardControl
                    , "space", new PauseScreen()));
        }

        // Stop running if block count is 0.
        if (this.blockCount.getValue() == 0) {
            scoreCount.increase(100);
            this.running = false;
        }
        if (this.ballCount.getValue() == 0) {
            this.running = false;
        }
    }

    /**
     * Draw all sprite objects.
     *
     * @param d - surface to draw on.
     */
    public void drawGame(DrawSurface d) {
        // Default background.
        this.sprites.drawAllOn(d);
    }

    /**
     * Stop the frame.
     *
     * @return - true or false(on\off).
     */
    public boolean shouldStop() {
        return !this.running;
    }


    /**
     * Remove object from collidable list.
     * Create list to prevent exception.
     *
     * @param c - Collidable object to remove.
     */
    public void removeCollidable(Collidable c) {
        ArrayList<Collidable> collidableList
                = new ArrayList<>(this.environment.getCollideArray());
        collidableList.remove(c);
        this.environment.setCollideArray(collidableList);

    }

    /**
     * Return block counts.
     *
     * @return - Counter for the block.
     */
    public Counter getBlockCount() {
        return blockCount;
    }

    /**
     * Return live counts.
     *
     * @return - Counter for the lives.
     */
    public Counter getLiveCount() {
        return liveCount;
    }

    /**
     * Remove object from sprite list.
     * Create list to prevent exception.
     *
     * @param s - Sprite object to remove.
     */
    public void removeSprite(Sprite s) {
        ArrayList<Sprite> spriteList
                = new ArrayList<>(this.sprites.getSpriteArray());
        spriteList.remove(s);
        this.sprites = new SpriteCollection(spriteList);
    }

    /**
     * Remove ball from balls list.
     * Create list to prevent exception.
     *
     * @param b - Collidable object ball to remove.
     */
    public void removeBall(Ball b) {
        ArrayList<Ball> ballArrayList
                = new ArrayList<>(this.balls);
        ballArrayList.remove(b);
        this.setBalls(ballArrayList);
    }

    /**
     * Set game listeners for blocks.
     *
     * @param stageSet - info for the game.
     */
    public void setBlockListen(LevelInformation stageSet) {

        BlockRemover blockListen
                = new BlockRemover(this, blockCount);
        ScoreTrackingListener scoreListen
                = new ScoreTrackingListener(this.scoreCount);

        for (HitNotifier block : stageSet.blocks()) {
            block.addHitListener(blockListen);
            block.addHitListener(scoreListen);
        }

    }

    /**
     * Set game listener for ball.
     *
     * @param stageSet - info for the game.
     */
    public void setBallListen(LevelInformation stageSet) {

        BallRemover ballListen = new BallRemover(this, ballCount);

        for (HitNotifier block : stageSet.getDeathBlock()) {
            block.addHitListener(ballListen);
        }
    }

    /**
     * Set balls list.
     *
     * @param setBalls - array to set.
     */
    public void setBalls(ArrayList<Ball> setBalls) {
        this.balls = setBalls;
    }


    /**
     * Set block count value(number of blocks).
     *
     * @param value - value to change to.
     */
    public void setBlockCount(Counter value) {
        this.blockCount = value;
    }

    /**
     * Set Bal count value(number of balls).
     *
     * @param value - value to change to.
     */
    public void setBallCount(Counter value) {
        this.ballCount = value;
    }

    /**
     * Initialize balls of the game start.
     *
     * @param radius - radius of the ball.
     */
    private void setStartBalls(int radius) {

        // Set the start point of the ball in the middle of the paddle.

        Point setStartP = moveBallToPaddle(this.paddle, radius);


        for (int i = 0; i < stage.numberOfBalls(); i++) {

            Ball ball = setNewBall(setStartP, radius, imageBall);
            ball.setVelocity(stage.initialBallVelocities().get(i));
        }


    }

    /**
     * Add new Ball to the game.
     *
     * @param point  - center of the ball.
     * @param radius - radius of the created ball.
     * @param image  - picture of the created ball.
     * @return Ball - created for the game.
     */
    private Ball setNewBall(Point point, int radius, Image image) {

        Ball ball = new Ball(point, radius, image);
        ball.setGameEnvi(this.environment);
        ball.addToGame(this);
        this.balls.add(ball);

        return ball;

    }


    /**
     * Find point to set the ball(middle top of the paddle).
     *
     * @param radius        -  distance from the paddle(radius).
     * @param placeToPaddle -  to move the ball on.
     * @return - point middle top of the paddle.
     */
    public Point moveBallToPaddle(Paddle placeToPaddle, int radius) {

        return new Point((
                placeToPaddle.getCollisionRectangle().getUpperLeft().getX()
                        + placeToPaddle.getCollisionRectangle().getWidth() / 2)
                , placeToPaddle.getCollisionRectangle().getUpperLeft(
        ).getY() - radius - 5);
    }

    /**
     * Add collidable objects to the list(set it).
     *
     * @param c - collidable object.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }


    /**
     * Add sprite object.
     *
     * @param s - object from type sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Set paddle for the game.
     *
     * @return - paddle for the game use.
     */
    public Paddle getPaddle() {
        final int paddleThick = 10, farFloor = 50;

        // Create a paddle(maybe in the future add one).
        Paddle paddleCreate = new Paddle((
                new Point(stage.getGuiWidth() / 2
                        - stage.paddleWidth() / 2
                        , stage.getGuiHeight() - farFloor)),
                stage.paddleWidth(), paddleThick, Color.YELLOW);
        // Set border (limit for the paddle moving.

        paddleCreate.setMaxMoveX(stage.getGuiWidth()
                , stage.getBorderThickness());

        paddleCreate.setPaddleSpeed(stage.paddleSpeed());

        return paddleCreate;
    }




    /**
     * Set image icon(balls as lives).
     */
    public void setImageIcon() {
        // Set image , read it from the folder and print it.
        try {
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream(
                    "game_images/dragonballball.png");
            this.imageBall = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            this.imageBall = null;
            System.out.print("\nCannot find ball, default on.");
        } finally {
            try {
                if (imageBall != null) {
                    imageBall.flush();
                }
            } catch (Exception e) {
                System.out.print("Failed to flush");
            }
        }
    }
}