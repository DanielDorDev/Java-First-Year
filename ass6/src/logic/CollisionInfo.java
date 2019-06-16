package logic;

import geometry.Point;
import logic.interfaces.Collidable;

/**
 * Class for Collision information.
 * @author Daniel dor.
 */
public class CollisionInfo {
    /**
     * geometry.Point of collision.
     */
    private Point collisPoint;
    /**
     * logic.interfaces.Collidable object info.
     */
    private Collidable collisObject;

    /**
     * Set the collision info.
     *
     * @param collisSetObject - set info about the collidable object.
     * @param collinPointSet  - set info about the intersect(collision) point.
     */
    public CollisionInfo(Collidable collisSetObject, Point collinPointSet) {
        this.collisObject = collisSetObject;
        this.collisPoint = collinPointSet;
    }

    /**
     * The point at which the collision occurs.
     *
     * @return - geometry.Point, represent the collision point.
     */
    public Point getCollisionPoint() {
        return collisPoint;
    }

    /**
     * The collidable object involved in the collision.
     *
     * @return - collidable object.
     */
    public Collidable getCollisionObject() {
        return this.collisObject;
    }

    /**
     * Change the Collision info if needed.
     *
     * @param change - new point to set.
     */
    public void setNewPoint(Point change) {
        this.collisPoint = change;
    }
}