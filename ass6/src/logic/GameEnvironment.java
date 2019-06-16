package logic;

import geometry.Line;
import geometry.Point;
import logic.interfaces.Collidable;

import java.util.ArrayList;

/**
 * Set game environment(space the ball will move).
 * @author Daniel dor.
 */
public class GameEnvironment {


    /**
     * logic.interfaces.Collidable array for the class.
     */
    private ArrayList<Collidable> collideArray = new ArrayList<>();

    /**
     * Construct array sprite.
     *
     * @param arrayS - array of type sprite.
     */
    public GameEnvironment(ArrayList<Collidable> arrayS) {
        if (arrayS != null) {
            this.collideArray.addAll(arrayS);
        }
    }
    /**
     * Add the given collidable to the environment.
     *
     * @param c - object from type logic.interfaces.Collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.collideArray.add(c);
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c - object from type logic.interfaces.Collidable to add.
     */
    public void addCollidableArray(ArrayList<Collidable> c) {
        this.collideArray.addAll(c);
    }

    /**
     * Search for collision between trajectory and an object.
     *
     * @param trajectory - line to check his intersect.
     * @return logic.CollisionInfo - info about intersect and point.
     * otherwise null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {

        Point closestPoint = null;
        Collidable closestObject = null;

        for (Collidable check : collideArray) {
            // Get closest point to collidable check object.
            Point tempPoint = trajectory.closestIntersectionToStartOfLine(
                    check.getCollisionRectangle());
            // Always be at least one collidable object(the borders).
            if (tempPoint != null) {

                // If it is the closest point and his on the trajectory line.
                // Set for the first point.
                if (closestPoint == null && trajectory.checkPoint(
                        trajectory, tempPoint)) {
                    // Set as closet point and copy the object.
                    closestPoint = tempPoint;
                    closestObject = check;
                } else {
                    // If it is the closet point set it.
                    // Check if it closer then the previous.
                    if (trajectory.checkPoint(trajectory, tempPoint)
                            && trajectory.start().distance(tempPoint)
                            < trajectory.start().distance(closestPoint)) {

                        closestPoint = tempPoint;
                        closestObject = check;
                    }
                }
            }
        } // return the closest object.
        return new CollisionInfo(closestObject, closestPoint);
    }

    /**
     * Return ArrayList of collidable objects.
     *
     * @return - arrayList contain type logic.interfaces.Collidable objects.
     */
    public ArrayList<Collidable> getCollideArray() {
        return collideArray;
    }
    /**
     * Set new collide array.
     * @param setCollideArray - collide array to set by.
     */
    public void setCollideArray(ArrayList<Collidable> setCollideArray) {
        this.collideArray = setCollideArray;
    }
}