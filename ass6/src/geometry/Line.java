package geometry;

import java.util.ArrayList;
/**
 * Class for geometry.Line.
 * @author Daniel dor.
 */
public class Line {

    /**
     * Members of the class geometry.Line, start, end, middle of a point.
     */
    private Point start, end, middle;
    /**
     * Slope , represent slope of the geometry.Line(can be double, infinity or NaN).
     * Vertical line = infinity.
     * Horizontal line = 0.
     * The line is a point = NaN
     */
    private Double slope;
    /**
     * PointB - is b point of equation y-y1 = m(x-x1) after transfer sides
     * y1-mx1 = b point.
     */
    private double pointB;

    /**
     * Construct a line using 2 existing points.
     * at the same time we set the pointB and slope values.
     *
     * @param startOfLine - starting point of the line
     * @param endOfLine   - ending point of the line
     */
    public Line(Point startOfLine, Point endOfLine) {
        // Set values of start point and end point for the line.
        this.start = startOfLine;
        this.end = endOfLine;
        // Set values of slope, pointB and middle point.
        setMembersValues();
    }

    /**
     * Construct line using coordinates (second constructor).
     * At the same time we set the pointB and slope values.
     *
     * @param x1 , x coordinate of the start point
     * @param y1 , y coordinate of the start point
     * @param x2 , x coordinate of the end point
     * @param y2 , y coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        // Set values of slope, pointB and middle point.
        setMembersValues();
    }

    /**
     * Set values of slope, B point and middle point.
     * slope - slope of the function.
     * middle - middle point of the line.
     * PointB - is b point of equation y-y1 = m(x-x1) after transfer sides
     * y1-mx1 = b point.
     */
    private void setMembersValues() {
        // Calculate slope with another function.
        slopeCalculate();
        // Calculate B point(more info in the member)
        this.pointB = (this.start.getY() - (this.slope * this.start.getX()));
        // Calculate middle point
        middleBuild();
    }

    /**
     * Use distance method in geometry.Point class to calculate length of line.
     *
     * @return double - length of the line.
     */
    public double length() {
        return (this.start.distance(end));
    }

    /**
     * Calculate middleX, middleY by divide the sum of 2 coordinates
     * inside the geometry.Line that just constructed.
     */
    private void middleBuild() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        this.middle = new Point(middleX, middleY);
    }

    /**
     * Returns the start point of the line.
     *
     * @return , start point
     */
    public Point start() {
        return (this.start);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return middle, the middle point of line
     */
    public Point middle() {
        return this.middle;
    }

    /**
     * Returns the end point of the line.
     *
     * @return , end point
     */
    public Point end() {
        return (this.end);
    }

    /**
     * Calculate slope using slope equation.
     * Slope can be a number, infinity(if x = 0), Nan(if geometry.Line is a point).
     */
    private void slopeCalculate() {
        // Slope = slopeY/slopeX (equation as learned in algebra).
        double slopeX = (this.end.getX() - this.start.getX());
        double slopeY = (this.end.getY() - this.start.getY());
        if (start.equals(end)) { // If the points are the same, no slope.
            this.slope = Double.NaN;
        } else if (slopeX == 0) { // If the slope vertical.
            this.slope = Double.POSITIVE_INFINITY;
        } else {
            this.slope = slopeY / slopeX;
        }
    }

    /**
     * Check for intersect between 2 lines, use another function.
     * If the function return point, it means there is intersect(true)
     * otherwise(if null) return false.
     *
     * @param other , another line to be compare to
     * @return Returns true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return (intersectionWith(other) != null);
    }

    /**
     * Use intermediate Theorem to check if the point is on the bounds of line.
     * I check if line start(x1,y1) & end(x2,y2) bound point(x,y) means:
     * x1 < x < x2 && y1 < y < y2, add epsilon(for the deviation of the java).
     *
     * @param line  , line to check on him if the point is in his area.
     * @param point , point to check if his on the line area
     * @return , True if it's on the line area, else false
     */
    public boolean checkPoint(final Line line, final Point point) {
        // geometry.Point (x,y) to check there bounds.
        double x = point.getX(), y = point.getY();
        final double e = 0.000000001; // Epsilon for the deviation.
        // Check = (x1<x && x<x2 && y1<y && y<y1)
        // I check twice(start x is bigger then end, and opposite(for y too)
        return ((((line.start.getX() <= x + e) && (x <= e + line.end.getX()))
                || ((line.end.getX() <= x + e) && (x <= e + line.start.getX())))
                && (((line.start.getY() <= y + e) && (y <= e + line.end.getY()))
                || ((line.end.getY() <= y + e)
                && (y <= e + line.start.getY()))));
    }

    /**
     * Check if current point is on the line.
     * Use another function checkPoint() to check if its on border.
     * I choose to use start point for the equation with point(x1\y1).
     *
     * @param line  - to check if the point is on him.
     * @param check - point to check if it's on the line.
     * @return geometry.Point - the intersect point if there is, else null.
     */
    private Point pointOnLine(final Line line, final Point check) {
        // Use regular line equation (y = mx + b )); put point(x,y).
        if (check.getY() == ((line.slope * check.getX()) + line.pointB)) {
            if (checkPoint(line, check)) {
                return check;
            } else { // If point isn't on the line border.
                return null;
            }
        } else { // If the point is not maintain the line equation.
            return null;
        }
    }

    /**
     * Search for intersect point , uses:  x = (b1-b2)/(m1-m2) equation.
     * Then we use first equation to calculate y(current line)
     * y = m1(x from above)+b1 ( default choose start point for liner equation).
     * All those are math equation learned in algebra.
     * After calculate intersect point if there is, check for borders.
     *
     * @param other - line to check for intersect point
     * @return geometry.Point - intersect point.
     */
    private Point searchIntersect(final Line other) {
        double x = (this.pointB - other.pointB) / (other.slope - this.slope);
        double y = ((this.slope * x) + this.pointB);
        Point intersect = new Point(x, y);
        if (checkPoint(this, intersect) && (checkPoint(other, intersect))) {
            return intersect;
        } else { // If point is not on one of the lines.
            return null;
        }
    }

    /**
     * Check intersect for vertical lines(a lot of edge cases).
     *
     * @param line , check if vertical line intersect him
     * @param ver  , vertical line to be checked
     * @return , point of intersect if there is, otherwise null.
     */
    private Point checkVertical(final Line line, final Line ver) {
        // the important coordinate in vertical line is the x(let's check him).
        // intersectY - the point of intersect.
        double verX = ver.start.getX();
        // If line is a point, we check if it's on vertical line.
        if (line.slope.isNaN()) {
            // If the point is on the same vertical x coordinate of the line.
            if (verX == line.start.getX()) {
                // We use coordinate y of the line(line = point).
                double y = line.start.getY();
                // Check if it inside border of vertical line.
                if ((((ver.start.getY() <= y) && (y <= ver.end.getY()))
                        || ((ver.end.getY() <= y) && (y <= ver.start.getY())))) {
                    return (new Point(verX, y));
                } else { // If its on x coordinate but not inside the segment.
                    return null;
                }
            } else {  // The point and the line has no common x.
                return null;
            }
        } else {  // Check for regular line (both are lines).
            // Calculate y using y = mx + b .
            double intersectY = ((line.slope * verX) + line.pointB);
            // Use intermediate Theorem, check if point is in segment.
            if (checkPoint(ver, new Point(verX, intersectY))) {
                return new Point(verX, intersectY);
            } else {  // If y is not on segment.
                return null;
            }
        }
    }

    /**
     * Find intersection with another line if there is.
     * Select to define - if a line made of a point is the same as another line
     * that is made of a point, there is an intersection.
     * If geometry.Line made of points is intersect another "normal" line, intersection.
     * I prefer represent point line as his start point.
     *
     * @param other , another line to find intersection with.
     * @return geometry.Point - the intersection point if the lines intersect,
     * and null otherwise.
     */
    public Point intersectionWith(Line other) {

        // 1) Check for lines = points, if there both the same return the point.
        if (this.slope.isNaN() && other.slope.isNaN()) { // Both are points.
            if (this.start.equals(other.start)) { // Check if points equal.
                return this.start();
            } else { // If both points but not equal.
                return null;
            }
            // 2) Check intersect points for vertical lines.
        } else if (this.slope.isInfinite() || other.slope.isInfinite()) {
            if (this.slope.isInfinite() && other.slope.isInfinite()) {
                // Both vertical = and don't touch in the border = no intersect.
                return edgeIntersect(other);
                // If one of the lines are vertical(check for intersect)
            } else if (this.slope.isInfinite()) {
                return checkVertical(other, this);
            } else {
                return checkVertical(this, other);
            }
            // 3) Check for lines that are parallel or horizontal lines.
        } else if (this.slope.equals(other.slope)) {
            return edgeIntersect(other);
        } else {
            // 4) Check intersection between line and a point(line is point).
            if (this.slope.isNaN()) {
                return pointOnLine(other, this.start);
                // another option if the other line is a point.
            } else if (other.slope.isNaN()) {
                return pointOnLine(this, other.start);
            } else { // 5) Check intersection between two lines.
                return searchIntersect(other);
            }
        }
    }

    /**
     * Check for equality in the lines(using geometry.Point.equals).
     * if the start and the end are the same i define it same lines.
     *
     * @param other - represent another geometry.Line(with points)
     * @return - boolean true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        //Check equality point start to point start or start to end.
        return (start.equals(other.start) && end.equals(other.end)
                || (start.equals(other.end) && end.equals(other.start)));
    }

    /**
     * Check intersect for edge lines, (vertical/parallel touching).
     *
     * @param other - another line to check for touching lines
     * @return geometry.Point - if there is intersect, otherwise null.
     */
    public Point edgeIntersect(Line other) {
        if (!(this.equals(other))) {
            // Check for lines equality, if no, check for touching border.
            if (this.start.equals(other.start)
                    || this.start.equals(other.end)) {
                return this.start;
            } else if (this.end.equals(other.start)
                    || this.end.equals(other.end)) {
                return this.end;
            } else {
                return null; // If there aren't touching in the border.
            }
        } else { // If lines are the same( define it as no intersect).
            return null;
        }
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line, check for distance to compare closest intersect.
     * There is only 3 option to intersect rectangle.
     * 0 - not intersect, 1 - intersect corner, 2- 2 points intersect.
     *
     * @param rect - rectangle to check for close intersect point.
     * @return - geometry.Point, null if there is no intersect.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        /*
        Use intersectionPoints to find list of intersection points
        and then use distance to calculate the closest point to the line.
         */
        ArrayList arrayPoint = (ArrayList) (rect.intersectionPoints(this));
        if (arrayPoint.isEmpty()) {
            return null;
        }
        // Check for the closest point.
        Point closestP = null;
        for (Object check : arrayPoint) {

            if (closestP == null) {
                closestP = (Point) check;
            } else {
                if (this.start.distance((Point) check)
                        < this.start.distance(closestP)) {
                    closestP = ((Point) check);
                }
            }
        }
        return closestP;
    }

    /**
     * Search for the closest point in  logic.CollisionInfo array).
     *
     * @param listP - list to check on him.
     * @return Return - closest point, there is no null(always will be point).
     */
    /*
    public geometry.Point closestIntersectionOfAnArrayOfPoints(
            final ArrayList<logic.CollisionInfo> listP) {

        // Start from first point(list is not empty).
        geometry.Point intersectP = listP.get(0).getCollisionPoint();

        // Check for all other points(if there is).
        for (int i = 1; i < listP.size(); i++) {
            // If distance is smaller , the intersectP = the smaller point.
            if (this.start.distance(intersectP)
                    > this.start.distance(listP.get(i).getCollisionPoint())) {
                intersectP = listP.get(i).getCollisionPoint();
            }
        }
        // Return the point.
        return intersectP;

    }
    */
}
