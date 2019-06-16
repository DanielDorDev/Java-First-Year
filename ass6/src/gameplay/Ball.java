package gameplay;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import logic.interfaces.Sprite;
import logic.GameEnvironment;
import logic.CollisionInfo;
import logic.GameLevel;
import java.awt.Image;
import java.awt.Color;
import java.util.Random;

/**
 * Class for object ball.
 * @author Daniel dor.
 */
public class Ball implements Sprite {

    /**
     * Center point of the ball object.
     */
    private Point point;
    /**
     * Radius of the ball object.
     */
    private int radius;
    /**
     * Color of the ball object.
     */
    private Image ballImage;
    /**
     * Random object useful.
     */
    private Random rand = new Random();

    /**
     * Every ball has a coordinate of movement, (dx,dy)/vector/(angles,speed).
     */
    private Velocity vector;
    /**
     * Environment of the game.
     */
    private GameEnvironment gameEnvironmentBall;

    /**
     * geometry.Line vector give the trajectory of the ball.
     */
    private CollisionInfo trajectory;

    /**
     * Construct ball(has radius,color and center point).
     * Set speed as chosen for the ball.
     * Set trajectory for the ball to move by.
     *
     * @param center      - a point represent the circle center
     * @param r           - radius of the ball
     * @param image - color of the ball
     */
    public Ball(Point center, int r, Image image) {
        this.point = center;
        this.radius = r;
        this.ballImage = image;
        setSpeed();
    }

    /**
     * Set game environment.
     * @param c - environment to set by.
     */
    public void setGameEnvi(GameEnvironment c) {
        this.gameEnvironmentBall = c;
        createTrajectory();
    }

    /**
     * Notify the sprite that time has passed.
     * @param dt - time passed.
     */
    public void timePassed(double dt) {
        moveOneStep(dt);
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param d - a surface to be drawn on
     */
    public void drawOn(DrawSurface d) {
        // Print circle.

        if (ballImage != null) {
            d.drawImage(this.getX(), this.getY(), this.ballImage);
        } else { //default.
            final int divide = 4;
            d.setColor(Color.white);
            d.fillCircle(this.getX(), this.getY(), this.getSize());
            d.setColor(Color.BLACK);
            d.drawCircle(this.getX(), this.getY(), this.getSize());
            d.setColor(Color.RED);
            d.fillCircle(this.getX(), this.getY(), this.getSize() / divide);
        }
    }

    /**
     * Change the location of a point.
     * Check if hits border(if so change direction)
     * @param dt - how much pixels to move in a second.
     */
    public void moveOneStep(final double dt) {

        // Next point that acquire after a moveOneStep.
        Point checkPoint = vector.applyToPoint(this.point, dt);

        /*
        Check if the ball next step is far from the distance between this point
        and the collision point(if it is , its will step inside a block(bad..)
        Check if the ball in his next move step will have lower distance then
        the radius(mean the ball will get too close to the point(sucked inside).
         */


            if ((checkPoint.distance(this.point) >= (
                    this.trajectory.getCollisionPoint().distance(this.point)))
                    || (checkPoint.distance(trajectory.getCollisionPoint())
                    <=  radius)) {

                // If so change velocity
                this.setVelocity(trajectory.getCollisionObject(
                ).hit(this, (trajectory.getCollisionPoint()), getVelocity()));

                //  Move the ball to the Collision point(not too close).
                this.point = this.trajectory.getCollisionObject(
                ).getCollisionRectangle().moveCloseToLine(
                        this.trajectory.getCollisionPoint(),
                        this.radius / 2);

            }

            // Set trajectory line, check every time(to hit the paddle).
            createTrajectory();
            // Move the ball :)
            this.point = vector.applyToPoint(this.point, dt);
        }

    /**
     * Set trajectory, by construct a very long line that will hit something.
     * (if not the blocks,it will hit the border).
     * Calculate the collision point to this collidable object.
     */
    private void createTrajectory() {

        // Set far int line.
        final int veryFarPoint = 1000;

        /*
        Start point is center, end point = dx & dy after 800 steps(far).
        Calculate the end point as the far possible place the ball could go.
        Combine start and end to a new line call preTrajectory.
        */
        Line preTraject = new Line(this.point, new Point(
                    this.vector.getDX() * veryFarPoint
                , vector.getDY() * veryFarPoint));


        // find the first obstacle that intersect the line.
        // Get Collision point and the object in logic.CollisionInfo object.
        this.trajectory = gameEnvironmentBall.getClosestCollision(preTraject);
    }

    /**
     * Create new velocity with exist velocity(v).
     *
     * @param v - exist velocity (has dx, dy)
     */
    public void setVelocity(Velocity v) {
        this.vector = v;
    }




    /**
     * Return the trajectory of the ball(hit object and the hit point.
     *
     * @return - line represent the trajectory.
     */
    /**
     * Return coordinate X.
     *
     * @return - int that is the x coordinate of a ball.
     */
    public int getX() {
        return (int) (this.point.getX());
    }

    /**
     * Return coordinate Y.
     *
     * @return - int that is the Y coordinate of a ball.
     */
    public int getY() {
        return (int) (this.point.getY());
    }

    /**
     * Return ball size (radius).
     *
     * @return - radius of the ball an int.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Return velocity of a ball.
     *
     * @return - the velocity of current ball.
     */
    public Velocity getVelocity() {
        return this.vector;
    }

    /**
     * Add ball to the game.
     *
     * @param g - game to add the current ball.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Set speed and angle.
     */
    public void setSpeed() {

        // Angle limit for the random & starting speed of the ball.
        final int angleLimit = 120, speedStart = 5;
        int angle = 60 - rand.nextInt(angleLimit);

        // Set speed.
        setVelocity(Velocity.fromAngleAndSpeed(angle, speedStart));
    }

    /**
     * Remove ball from the game.
     * @param g - game to remove from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}
