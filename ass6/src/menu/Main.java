package menu;

import animation.MenuAnimation;
import biuoop.GUI;
import biuoop.Sleeper;
import levels.LevelInformation;
import levels.SetInfo;
import levels.LevelSpecificationReader;
import logic.Counter;
import logic.HighScoresTable;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import logic.GameFlow;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

/**
 * Class for main run.
 */
public class Main {

    private static final int GAME_WIDTH = 800; // Game width.
    private static final int GAME_HEIGHT = 600; // Game high.
    private static final int FPS = 60; // Frame per second.
    private static final int TABLE_SIZE = 5; // Table size.
    private static final int LIVE_START = 7;
    private static final String GUI_MSG = "Arkanoid - Created By DD";
    private static final String WELCOME_MSG = "Welcome to Dragonoid";
    private static final String TABLE_NAME = "highscores"; // Table name.
    private static final String DEFAULT_PATH = "level_sets.txt";
    private static final String LEVEL_MSG = "Choose your level:";


    /**
     * Start the game.
     *
     * @param args - source for the game levels.
     */
    public static void runMain(String[] args) {

        String path;

        if (args.length > 0) { // Check for path from command line.
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg);
            }
            path = sb.toString();
        } else { // Put default path.
            path = DEFAULT_PATH;
        }
        GUI gui = new GUI(GUI_MSG, GAME_WIDTH, Main.GAME_HEIGHT);

        AnimationRunner runner = new AnimationRunner(gui, FPS, new Sleeper());

        HighScoresTable highScoresTable = new HighScoresTable(TABLE_SIZE);

        try { // Open high score table.
            highScoresTable.load(new File(TABLE_NAME));
        } catch (IOException e) {
            try { // If failed open a new one.
                highScoresTable.save(new File(TABLE_NAME));

            } catch (IOException x) { // If failed saving.
                System.out.print("Failed to create save in start of the game");
            }
        }
        // Main menu.
        Menu<Task<Void>> openMenu = new MenuAnimation<>(runner, WELCOME_MSG,
                gui.getKeyboardSensor());
        // Sub menu.
        Menu<Task<Void>> levelMenu = new MenuAnimation<>(runner, LEVEL_MSG,
                gui.getKeyboardSensor());
        // Set info(every set of levels).
        SetInfo info = new SetInfo(path);
         for (int i = 0; i < info.getSize(); i++) {

             int move = i; // Go by the info array.
             levelMenu.addSelection(info.getKey(i), info.getName(i)
                     , new Task<Void>() { // Create a anonymous class.
                         public Void run() { // Run task.
                             // Create a game flow.
                             GameFlow gamePlay = new GameFlow(
                                 runner, highScoresTable, gui
                                 , new Counter(LIVE_START)
                                 , gui.getKeyboardSensor()
                                 , TABLE_SIZE, TABLE_NAME);

                             // Create for any set level a level array.
                             ArrayList<LevelInformation> levels
                                     = new ArrayList<>();
                             try {
                                 InputStream is =
                                         ClassLoader.getSystemClassLoader(
                                         ).getResourceAsStream(info.getPath(
                                         move));
                                 levels.addAll(new LevelSpecificationReader(
                                 ).fromReader(new InputStreamReader(is)));
                                 is.close();
                             } catch (Exception e) {
                                 System.err.println("Failed to create the level"
                                         + info.getPath(move));
                             }
                             // Run the levels.
                             gamePlay.runLevels(levels);
                             return null;
                         }
                     });
         }
         // Add the sub menu with levels.
        openMenu.addSubMenu("s", "(S)tart", levelMenu);
         // Add high score selection.
        openMenu.addSelection("h", "(H)igh Score"
                , new ShowHiScoresTask(runner
                        , new KeyPressStoppableAnimation(gui.getKeyboardSensor()
                        , "space"
                        , new HighScoresAnimation(highScoresTable))));
        // Quit bottom.
        openMenu.addSelection("q", "(Q)uit", new ExitTask());
        // Run the main menu.
        while (true) {
            openMenu.resetData();
            runner.run(openMenu);

            openMenu.getStatus().run();
        }
    }
}
