package geometry;

import java.util.ArrayList;
/**
 * Class for object geometry.Rectangle.
 * @author Daniel dor.
 */
public class Rectangle {

    /**
     * upperLeftP = geometry.Point of start rectangle.
     */
    private Point upperLeftP;
    /**
     * widthX = width of the rectangle(by x coordinate).
     * heightY = height of the rectangle(by y coordinate).
     */
    private double widthX, heightY;
    /**
     * The rectangle made of lines , i want to border it with them.
     * Use them for finding intersection(maybe they will useful later course).
     */
    // left = 0, right = 1, upper = 2, floor = 3;
    private ArrayList<Line> lineArray = new ArrayList<>();


    /**
     * Create a new rectangle with location and width/height.
     * Build him lines(borders).
     *
     * @param upperLeft - Point of the upperLeft (start of the rectangle)
     * @param width     - width of the rectangle.
     * @param height    - height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeftP = upperLeft;
        this.widthX = width;
        this.heightY = height;
        // Build lines of the geometry.Rectangle.
        this.buildLines(upperLeft, width, height);
    }

    /**
     * I use the data from the constructor to build our 4 lines.
     *
     * @param upperLeft - Point of the upperLeft(start of the rectangle)
     * @param width     - width of the rectangle.
     * @param height    - height of the rectangle.
     */
    private void buildLines(Point upperLeft, double width, double height) {
        /*
         corner1(not written) is upper left point.
         corner2 is up+right corner, corner3 is floor+left corner
         corner4 is floor+right corner, written as separate for better view.
         1----------2
         |          |
         |   Rect   |
         3----------4
         */
        Point corner2 = new Point(
                upperLeft.getX() + width, upperLeft.getY());
        Point corner3 = new Point(
                upperLeft.getX(), upperLeft.getY() + height);
        Point corner4 = new Point(upperLeft.getX() + width
                , upperLeft.getY() + height);

        // Construct lines, using corner points.
        this.lineArray.add(new Line(upperLeft, corner3)); // Left line.
        this.lineArray.add(new Line(corner2, corner4)); // Right line.
        this.lineArray.add(new Line(upperLeft, corner2)); // Upper line.
        this.lineArray.add(new Line(corner3, corner4)); // Floor line.

    }

    /**
     * Check for intersection between rectangle and line.
     *
     * @param line - line to check intersection with.
     * @return List - list of intersection points with the current line.
     */
    public java.util.List intersectionPoints(Line line) {

        // Create list to store the points.
        ArrayList<Point> pointList = new ArrayList<>();
        // They is only 2 points possible for intersect.
        for (Line check : this.lineArray) {
            Point intersectP = line.intersectionWith(check); // Check intersect.
            // If the point is not null(mean there is intersect, add it).
            if (intersectP != null) {
                pointList.add(intersectP);
            }
        } // Return even if empty.
        return pointList;

    }

    /**
     * Return line from the rectangle.
     *
     * @param name - name of the line(side).
     * @return geometry.Line - line that we asked for.
     */
    public Line getLine(String name) {

        final int left = 0, right = 1, upper = 2, lower = 3;
        // Switch case for the line to return.
        switch (name) {
            case "Left":
                return this.lineArray.get(left);
            case "Right":
                return this.lineArray.get(right);
            case "Upper":
                return this.lineArray.get(upper);
            default:  // for down line.
                return this.lineArray.get(lower);
        }
    }

    /**
     * Return the width of the rectangle.
     *
     * @return - double , represent width of the rectangle
     */
    public double getWidth() {
        return this.widthX;
    }

    /**
     * Return height of the rectangle.
     *
     * @return - double , represent height of the rectangle
     */
    public double getHeight() {
        return this.heightY;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return - geometry.Point, represent the start point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeftP;
    }

    /**
     * Return the list of arrays if needed.
     *
     * @return ArrayList List type .
     */
    public ArrayList<Line> getLineArray() {
        return lineArray;
    }

    /**
     * A function to set a point the correct rectangle line(move near).
     * The Ass6Game use is for moving the ball(better look).
     *
     * @param checkThisOnLine - what point to check(what line he intersect).
     * @param howMuchMove     - how much move(x,y axis).
     * @return - return new point if changed, otherwise the same point.
     */
    public Point moveCloseToLine(Point checkThisOnLine, int howMuchMove) {

        final int left = 0, right = 1, upper = 2, lower = 3;

        // Move by line(if point is on the line.
        if (this.getLineArray().get(left).checkPoint(
                this.getLineArray().get(left), checkThisOnLine)) {
            return new Point(checkThisOnLine.getX()
                    - howMuchMove, checkThisOnLine.getY());

        } else if (this.getLineArray().get(right).checkPoint(
                this.getLineArray().get(right), checkThisOnLine)) {
            return new Point(checkThisOnLine.getX()
                    + howMuchMove, checkThisOnLine.getY());

        } else if (this.getLineArray().get(upper).checkPoint(
                this.getLineArray().get(upper), checkThisOnLine)) {
            return new Point(checkThisOnLine.getX(),
                    checkThisOnLine.getY() - howMuchMove);

        } else if (this.getLineArray().get(lower).checkPoint(
                this.getLineArray().get(lower), checkThisOnLine)) {

            return new Point(checkThisOnLine.getX(),
                    checkThisOnLine.getY() + howMuchMove);

        } else {
            return checkThisOnLine;
        }
    }
}