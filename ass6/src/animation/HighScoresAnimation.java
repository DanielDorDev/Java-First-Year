package animation;
import biuoop.DrawSurface;
import geometry.Point;
import logic.HighScoresTable;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class for high score animation.
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scoreToDisplay; // Score table.
    private Image imageBoard; // Image for the background.
    private boolean stopGame; // animation stop.

    /**
     * Construct animation of high score.
     *
     * @param scores - score table loaded.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scoreToDisplay = scores;
        setImageIcon();
        this.stopGame = false; // default for stopping animation.
    }

    /**
     * Make a frame.
     *
     * @param d  - surface that frame will be cast on.
     * @param dt - time passed.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        final int fontSize = 20;
        final int middleMove = 100;

        if (imageBoard != null) {
            d.drawImage(0, 0, this.imageBoard);

        } else {
            d.setColor(Color.black);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }

        new TextFill().printText(d, "High Score:", fontSize * 2
                , Color.YELLOW, Color.ORANGE.darker()
                , new Point(d.getWidth() / 2 - middleMove
                        , d.getHeight() / 5));

        for (int i = 0; i < this.scoreToDisplay.size(); i++) {
            String playerName
                    = scoreToDisplay.getHighScores().get(i).getName();
            Integer playerScore
                    = scoreToDisplay.getHighScores().get(i).getScore();
            final int highPrintName = d.getHeight() / 3 + 2 * fontSize * i;

            new TextFill().printText(d, Integer.toString(i + 1) + "."
                    , fontSize, Color.white, Color.gray
                    , new Point(d.getWidth() / 3 - fontSize * 2
                            , highPrintName));

            new TextFill().printText(d, playerName, fontSize
                    , Color.white, Color.black
                    , new Point(d.getWidth() / 3, highPrintName));

            new TextFill().printText(d, playerScore.toString(), fontSize
                    , Color.white, Color.black
                    , new Point(d.getWidth() / 3 + middleMove * 3
                            , highPrintName));

            new TextFill().printText(d, "Press space to continue"
                    , fontSize, Color.white, Color.black
                    , new Point(d.getWidth() / fontSize
                            , d.getHeight() - fontSize));
        }
    }

    /**
     * Stop the frame.
     *
     * @return - true or false(on\off).
     */
    @Override
    public boolean shouldStop() {
        return stopGame;
    }


    /**
     * Set Game image.
     */
    public void setImageIcon() {
        // Set image , read it from the folder and print it.
        try {
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream(
                    "background_images/TopScore.jpeg");
            this.imageBoard = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            this.imageBoard = null;
        } finally {
            try {
                if (imageBoard != null) {
                    imageBoard.flush();
                }
            } catch (Exception e) {
                System.out.print("Failed to flush");
            }
        }
    }
}
