package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Class for run animation.
 */
public class AnimationRunner {
    private GUI gui;
    private double framesPerSecond; //set frame for seconds.
    private Sleeper sleeper;

    /**
     * Construct the animation class.
     *
     * @param g     - gui of the game.
     * @param fps   - set frame per seconds for the game.
     * @param sleep - set sleeper.
     */
    public AnimationRunner(GUI g, double fps, Sleeper sleep) {
        this.gui = g;
        this.framesPerSecond = fps;
        this.sleeper = sleep;
    }

    /**
     * Run the animation(another class from type animation).
     *
     * @param animation - animation to run.
     */
    public void run(Animation animation) {

        final int milliSecondsInSecond = 1000;
        double millisecondsPerFrame = milliSecondsInSecond / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, millisecondsPerFrame
                    / milliSecondsInSecond); // print the frame.

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep
                    = (long) millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Return gui of the current animation.
     *
     * @return Gui object.
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * Return sleeper.
     *
     * @return - sleeper object.
     */
    public Sleeper getSleeper() {
        return sleeper;
    }
}

