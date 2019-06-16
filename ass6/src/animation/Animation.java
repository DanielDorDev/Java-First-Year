package animation;

import biuoop.DrawSurface;

/**
 * Interface fir animation objects.
 */
public interface Animation {
    /**
     * Make a frame.
     * @param d - surface that frame will be cast on.
     * @param dt - time passed.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Stop the frame.
     * @return - true or false(on\off).
     */
    boolean shouldStop();
}
