package geometry;

/**
 * Class for geometry.Point.
 * The class has members x,y that set coordinates.
 * Construct that set (x,y) values.
 * Method for returning x,y values, check equality, calculate distance.
 * @author Daniel dor.
 */

public class Point {

    /**
     * Values of point(coordinates x,y).
     */

    private double x, y;

    /**
     * Constructor - put values into the member.
     *
     * @param changeX - set value x
     * @param changeY - set value y
     */
    public Point(double changeX, double changeY) {
        this.x = changeX;
        this.y = changeY;
    }

    /**
     * Calculate distance between 2 points.
     *
     * @param other - another point for the calculation.
     * @return double - the distance between 2 points
     */
    public double distance(Point other) {

        // Get values from other point
        double x2 = other.getX(), y2 = other.getY();
        // Get values from current point
        double x1 = this.x, y1 = this.y;
        // Calculate and return distance.
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * Compare between two points(check for equality).
     *
     * @param other - another point to compare with.
     * @return -  return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return ((other.getX() == this.x) && (other.getY() == this.y));
    }

    /**
     * Return the x value of current point.
     *
     * @return double - represent the value of x
     */
    public double getX() {
        return this.x;

    }

    /**
     * Return the  y value of current point.
     *
     * @return double - represent the value of y
     */
    public double getY() {
        return this.y;
    }
}