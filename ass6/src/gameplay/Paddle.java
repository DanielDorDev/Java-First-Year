package gameplay;
import logic.GameLevel;
import logic.interfaces.Collidable;
import logic.interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Line;
import java.awt.Color;

/**
 * Paddle Class.
 */
public class Paddle implements Sprite, Collidable {

    /**
     * Limit movement x coordinate.
     */
    private double minMoveX, maxMoveX;
    /**
     * Keyboard sensor.
     */
    private biuoop.KeyboardSensor keyboard;

    /**
     * paddle is made of the rectangle.
     */
    private Rectangle paddle, paddleCopy;

    /**
     * Control the color of the paddle.
     */
    private Color paddleColor;
    /**
     * Paddle moving speed.
     */
    private double paddleSpeed;

    /**
     * Set the paddle data.
     *
     * @param start    - starting point(upper left corner).
     * @param widthX   - width of the paddle.
     * @param heightY  - height of the paddle.
     * @param colorSet - color of the paddle.
     */
    public Paddle(Point start, double widthX, double heightY, Color colorSet) {

        final int difSpeed = 5;
        this.paddle = new Rectangle(start, widthX, heightY);
        this.paddleColor = colorSet;
        this.paddleSpeed = difSpeed; // Default speed.
        this.paddleCopy = paddle;
    }

    /**
     * Print the paddle.
     *
     * @param d - surface to draw on.
     */
    public void drawOn(DrawSurface d) {

        // Fill paddle by color set.
        d.setColor(this.paddleColor);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX() // x point.
                , (int) this.paddle.getUpperLeft().getY() // y point.
                , (int) this.paddle.getWidth() // width.
                , (int) this.paddle.getHeight()); // height.
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.paddle.getUpperLeft().getX() // x point.
                , (int) this.paddle.getUpperLeft().getY() // y point.
                , (int) this.paddle.getWidth() // width.
                , (int) this.paddle.getHeight()); // height.
        d.setColor(Color.GRAY);
    }

    /**
     * Paddle speed set.
     *
     * @param setPaddleSpeed - paddle speed to test.
     */
    public void setPaddleSpeed(int setPaddleSpeed) {
        this.paddleSpeed = setPaddleSpeed;
    }

    /**
     * Notify the sprite that time has passed.
     * Use keyboard sensor to handle special actions on object.
     * Can move left, right, space(make the move faster).
     *
     * @param dt - time passed.
     */

    public void timePassed(double dt) {

        if (keyboard.isPressed((KeyboardSensor.LEFT_KEY))
                || keyboard.isPressed("A") || keyboard.isPressed("a")) {

            if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                this.moveLeft(dt);
            }


            this.moveLeft(dt);
        }

        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                || keyboard.isPressed("D") || keyboard.isPressed("d")) {
            if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                this.moveRight(dt);
            }
            this.moveRight(dt);
        }
    }

    /**
     * Make a move left of a paddle.
     *
     * @param dt - time passed.
     */
    public void moveLeft(double dt) {
        // Check if the paddle got to his limit.
        if (this.minMoveX < this.paddle.getUpperLeft().getX() - dt
                * paddleSpeed) {
            // Move it(set the place).
            this.paddle = new Rectangle(new Point(
                    this.paddle.getUpperLeft().getX() - dt * paddleSpeed,
                    this.paddle.getUpperLeft().getY()),
                    this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * Make a move right of a paddle.
     *
     * @param dt - time passed.
     */
    public void moveRight(double dt) {
        if (this.maxMoveX > this.paddle.getUpperLeft().getX()
                + dt * paddleSpeed) {

            // Check if the paddle got to his limit.
            this.paddle = new Rectangle(new Point(
                    this.paddle.getUpperLeft().getX() + dt * paddleSpeed,
                    this.paddle.getUpperLeft().getY()),
                    this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * Hit function(what happen when hit was made.
     *
     * @param collisionPoint  - point of collision.
     * @param currentVelocity - the current velocity of the collidable class.
     * @param hitter          - ball that hit the paddle.
     * @return Velocity - new geometry.Velocity(new vector).
     */
    public Velocity hit(Ball hitter, Point collisionPoint
            , Velocity currentVelocity) {
        // Call a line and check if point is on him.
        // I prefer write it as member for easy to read.
        Line left = paddle.getLine("Left");
        Line right = paddle.getLine("Right");
        Line upper = paddle.getLine("Upper");
        Line floor = paddle.getLine("Floor");

        double dx = currentVelocity.getDX(), dy = currentVelocity.getDY();
        // Check if line hits the paddle left\right line.
        if ((left.checkPoint(left, collisionPoint) // Hit left or right line.
                || (right.checkPoint(right, collisionPoint)))) {

            currentVelocity.changeVec(-1 * dx, 1 * dy);

        } else if (floor.checkPoint(floor, collisionPoint)) { // Floor line.

            currentVelocity.changeVec(dx, -1 * dy);

        } else { // For upper or floor lines(less for upper).
            // Make more fun direction(by the part of the block that got hit).
            double length = upper.length(); // Length of the paddle.
            double xAxis = this.paddle.getUpperLeft().getX();
            double speed = Math.sqrt((dx * dx) + (dy * dy));
            double angle = -60 + 120 / length * (collisionPoint.getX() - xAxis);

            return Velocity.fromAngleAndSpeed(angle, speed);
        }
        return currentVelocity;
    }

    /**
     * Return the rectangle that build the paddle.
     *
     * @return - return type rectangle object.
     */

    public Rectangle getCollisionRectangle() {
        return this.paddle;

    }

    /**
     * Set key board sensor.
     *
     * @param key - KeyboardSensor to set by.
     */
    public void setKeyBoard(final KeyboardSensor key) {

        this.keyboard = key;
    }

    /**
     * Add paddle to the game.
     *
     * @param g - game to add the current paddle.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Set the paddle moving limits.
     *
     * @param widthGui   - width of gui (game).
     * @param widthBoard - width of the board(border of the game).
     */
    public void setMaxMoveX(int widthGui, int widthBoard) {
        this.maxMoveX = (widthGui - widthBoard - this.paddle.getWidth());
        this.minMoveX = widthBoard;
    }

    /**
     * Set new place to the paddle.
     *
     * @param nPaddle - new place(rectangle "copy it").
     */
    public void setPaddle(Rectangle nPaddle) {
        this.paddle = nPaddle;
    }

    /**
     * Return the rectangle copy(starting place).
     *
     * @return - Rectangle object.
     */
    public Rectangle getPaddleCopy() {
        return this.paddleCopy;
    }
}

            /* old paddle mover.
            if (collisionPoint.getX() <= length / devLen + xAxis) {

                return Velocity.fromAngleAndSpeed(-60, speed); // Left.

            } else if (collisionPoint.getX() <= 2 * length / devLen + xAxis) {

                return Velocity.fromAngleAndSpeed(-30, speed);


            } else if (collisionPoint.getX() >= 4 * length / devLen + xAxis) {

                return Velocity.fromAngleAndSpeed(30, speed); // Right


            } else if (collisionPoint.getX() >= 3 * length / devLen + xAxis) {

                return Velocity.fromAngleAndSpeed(60, speed);


            } else { // For the middle.
                currentVelocity.changeVec(1 * dx, -1 * dy);
            }
            */