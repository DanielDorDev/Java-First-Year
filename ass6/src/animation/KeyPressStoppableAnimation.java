package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Key stoppable animation(prevent double clicking).
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyBoard;
    private String keyToPress;
    private Animation animateIt;
    private boolean isAlreadyPressed;
    private boolean stopIt;
    private boolean firstFrame;

    /**
     * Construct key press stoppable animation.
     * @param sensor - key sensor.
     * @param key - key to prevent stop.
     * @param animation - animation to run(and prevent stoping).
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key
            , Animation animation) {
        this.keyBoard = sensor;
        this.keyToPress = key;
        this.animateIt = animation;
        this.isAlreadyPressed = true; // if it already pressed.
        stopIt = false; // stop it or not.
        firstFrame = true; // first time.
    }

    /**
     * Make a frame.
     *
     * Check if key already pressed and prevent stopping of the game.
     * @param d  - surface that frame will be cast on.
     * @param dt - time passed.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // if enter the do one frame , it from now on will not be first frame.
        firstFrame = false;
        // Check pressing.
        if (!this.keyBoard.isPressed(keyToPress) && isAlreadyPressed) {
            isAlreadyPressed = false;
        }
        this.animateIt.doOneFrame(d, dt);
        // Check for second time when its not the first pressing.
        if (!this.isAlreadyPressed && keyBoard.isPressed(this.keyToPress)) {
            stopIt = true;
        }
    }

    /**
     * Stop the frame(if key is pressed).
     *
     * @return - true or false(on\off).
     */
    @Override
    public boolean shouldStop() {
        if (firstFrame) { // If first frame don't stop.
            stopIt = false;
        }
        if (stopIt) { //If stop it is true(stop the game).
            firstFrame = true;
        }
        return stopIt;
    }
}