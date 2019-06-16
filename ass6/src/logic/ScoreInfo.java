package logic;

import java.io.Serializable;

/**
 * Class for score info.
 */
public class ScoreInfo implements Serializable {

    private String nameOfPlayer;
    private int scoreOfPlayer;

    /**
     * Construct score info.
     *
     * @param name  - name of player.
     * @param score - score for the player.
     */
    public ScoreInfo(String name, int score) {
        this.nameOfPlayer = name;
        this.scoreOfPlayer = score;
    }

    /**
     * Return name of the player.
     *
     * @return - String , name of player.
     */
    public String getName() {
        return this.nameOfPlayer;
    }

    /**
     * Return score of the player.
     *
     * @return - Int , score number.
     */
    public int getScore() {
        return this.scoreOfPlayer;
    }
}