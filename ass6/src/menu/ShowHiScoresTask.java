package menu;

import animation.Animation;
import animation.AnimationRunner;

/**
 * High score task.
 */
public class ShowHiScoresTask implements Task<Void> {


    private final AnimationRunner runner;
    private final Animation highScoresAnimation;

    /**
     * Construct high score show task.
     * @param addRunner - runner animation.
     * @param addHighScoresAnimation - high score animation.
     */
    public ShowHiScoresTask(AnimationRunner addRunner
            , Animation addHighScoresAnimation) {
        this.runner = addRunner;
        this.highScoresAnimation = addHighScoresAnimation;
    }

    /**
     * Run the task(show the high score table).
     * @return - noting.
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}