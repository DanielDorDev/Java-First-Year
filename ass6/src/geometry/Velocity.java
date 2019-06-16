package geometry;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 * @author Daniel dor.

 */
public class Velocity {

    /**
     * Change occur with a point, kind of basic base vector(1,0) , (0,1).
     */
    private double dx, dy;

    /**
     * Set the dx,dy( specifies the change in position of point).
     *
     * @param changeDx - how much move the x coordination.
     * @param changeDy - how much move the y coordination.
     */
    public Velocity(double changeDx, double changeDy) {

        this.dx = changeDx;
        this.dy = -changeDy; // - because we want the 0 angle to go up.

    }

    /**
     * Take a point with position (x,y) and return a new point.
     *
     * Use dt as a coefficient of time.
     * @param p  , a point to operate a move on him.
     * @param dt , how much frames move in second.
     * @return , new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + this.dx * dt
                , p.getY() + this.dy * dt);
    }

    /**
     * Convert angle and speed to more convenient members (dx,dy).
     * Angle is calculated by 0 angle goes up.
     *
     * @param angle - angle of velocity.
     * @param speed - speed of velocity.
     * @return - new velocity object define by dx & dy.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        // Use geometric features to convert .
        double dyCalc = speed * Math.cos(Math.toRadians(angle));

        double dxCalc = speed * Math.sin(Math.toRadians(angle));

        return (new Velocity(dxCalc, dyCalc));

    }

    /**
     * Return the dx of a velocity.
     *
     * @return - The current dx.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * Return the dy of a velocity.
     *
     * @return - The current dy.
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * Change vector if needed.
     *
     * @param dxChange - new dx to convert to.
     * @param dyChange - new ddy to convert to.
     * @return
     */
    public void changeVec(double dxChange, double dyChange) {
        this.dx = dxChange;
        this.dy = dyChange;
    }
}
