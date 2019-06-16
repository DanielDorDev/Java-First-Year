package logic;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import levels.LevelInformation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class for game flow.
 */
public class GameFlow {



    private Counter lives; // Number of lives.
    private GUI gui; // Gui of game.
    private KeyboardSensor keyBoard;
    private AnimationRunner runner;
    private Counter score;
    private HighScoresTable highScoreLoad; // High score info.
    private int size;
    private String nameTable; // Name of table to load.

    /**
     * Construct game flow.
     * @param runOneGame   - animation that run the game.
     * @param gui          - gui of the game.
     * @param numOfLives   - number of lives to start with.
     * @param keyboardMain - keyBoard sensor.
     * @param sizeTable - max size of the table score.
     * @param tableName - name of the high score table.
     * @param highScoresTable - high score info.

     */
    public GameFlow(AnimationRunner runOneGame, HighScoresTable highScoresTable
            , GUI gui, Counter numOfLives, KeyboardSensor keyboardMain
            , int sizeTable, String tableName) {

        lives = numOfLives;

        keyBoard = keyboardMain;

        this.runner = runOneGame;

        this.gui = gui;

        score = new Counter(0); // set 0 score.

        this.highScoreLoad = highScoresTable;

        this.size = sizeTable;

        this.nameTable = tableName;
    }

    /**
     * Run the levels of the game.
     *
     * @param levels - level to run.
     */
    public void runLevels(List<LevelInformation> levels) {

        boolean didWin = true;


        // Go on the array of levels.
        for (LevelInformation levelInfo : levels) {

            // Set new game for the level.
            GameLevel level = new GameLevel();

            // Initialize the game.
            level.initialize(levelInfo, runner, lives, keyBoard, score);

            // Stop if no blocks left, or lives.
            while (level.getLiveCount().getValue() > 0
                    && level.getBlockCount().getValue() > 0) {
                level.run();
            }
            // If no more lives left stop game with losing(false).
            if (level.getLiveCount().getValue() == 0) {
                didWin = false;
                break;
            }

        }
        runner.run(new KeyPressStoppableAnimation(this.keyBoard, "space"
                , new EndScreen(this.score, didWin)));

        // If the rank is in the top rank within size of table.
        if (this.highScoreLoad.getRank(this.score.getValue()) <= size) {
            // Ask for name.

            // Ask for name.
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("New high score:"
                    , "What is your name?", "");

            // Trim size to fit high score table.
            if (name.length() > 25) {
                name = name.substring(0, 25);
            }


            this.highScoreLoad.add(new ScoreInfo(name, this.score.getValue()));
            try {
                this.highScoreLoad.save(new File(nameTable));
            } catch (IOException e) {
                System.out.print("Failed to save current score");
            }
        }
        runner.run(new KeyPressStoppableAnimation(this.keyBoard
                , "space", new HighScoresAnimation(this.highScoreLoad)));
    }


}
