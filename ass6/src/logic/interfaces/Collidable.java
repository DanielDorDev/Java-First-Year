package logic.interfaces;

import gameplay.Ball;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * Interface of collidable objects.
 * @author Daniel dor.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return - return the rectangle(represent the shape of object).
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param collisionPoint  - point of collision.
     * @param currentVelocity - the current velocity of the collidable class.
     * @param hitter - ball that made the hit.
     * @return - return is the new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}