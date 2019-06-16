package logic;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class for high score table.
 */
public class HighScoresTable implements Serializable {

    private ArrayList<ScoreInfo> arrayScoreInfo;
    private int maxNumberOfScore;

    /**
     * Create an empty high-scores table and set size.
     *
     * @param size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.arrayScoreInfo = new ArrayList<>();
        this.maxNumberOfScore = size;
    }

    /**
     * Add a high-score.
     * I prefer not using getRank(there is not demand) easier add and remove.
     *
     * @param score - a score info to add(name and score).
     */
    public void add(ScoreInfo score) {
        this.arrayScoreInfo.add(score);
        // Sort the list.
        this.arrayScoreInfo.sort(new Comparator<ScoreInfo>() {
            @Override
            public int compare(logic.ScoreInfo o1, logic.ScoreInfo o2) {
                return o2.getScore() - o1.getScore();
            }
        });        // If the array is bigger then defined.
        if (this.arrayScoreInfo.size() > maxNumberOfScore) {
            this.arrayScoreInfo.remove(this.arrayScoreInfo.size() - 1);
        }
    }

    /**
     * Return table size.
     *
     * @return - arrayScoreInfo size.
     */
    public int size() {
        return this.arrayScoreInfo.size();
    }

    /**
     * Return the array of scores, high score to low(already sorted).
     *
     * @return - array of score info.
     */
    public List<ScoreInfo> getHighScores() {
        return this.arrayScoreInfo;
    }

    /**
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low.
     *
     * @param score - score to check.
     * @return - return the rank of the current score
     */
    public int getRank(int score) {
        ArrayList<Integer> arrayScoresRank = new ArrayList<>();
        for (ScoreInfo scoreAdd : this.arrayScoreInfo) {
            arrayScoresRank.add(scoreAdd.getScore());
        }
        arrayScoresRank.add(score);
        arrayScoresRank.sort(Collections.reverseOrder());
        return arrayScoresRank.indexOf(score);
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.arrayScoreInfo.clear();
    }

    /**
     * Save table data to specified file.
     *
     * @param filename - file
     * @throws IOException - if something happened in the saving.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream tableSave = null;
        try {
            tableSave = new ObjectOutputStream(new FileOutputStream(filename));
            tableSave.writeObject(this);
        } catch (IOException e) {
            System.out.print("Problem with writing to the file");
        } finally {
            if (tableSave != null) {
                tableSave.close();
            }
        }
    }

    // Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param filename - to read from.
     * @return High score object table if found.
     * @throws IOException - if failed opening.
     */
    public logic.HighScoresTable loadFromFile(File filename)
            throws IOException {
        ObjectInputStream tableLoad = null;

        try {
            tableLoad = new ObjectInputStream(new FileInputStream(filename));
            return (HighScoresTable) tableLoad.readObject();
        } catch (ClassNotFoundException e) {
            System.out.print("File cannot be found");
            return null;
        } finally {
            if (tableLoad != null) {
                try {
                    tableLoad.close();
                } catch (Exception e) {
                    System.out.print("Cannot close file");
                }
            }
        }
    }

    // Load table data from file.
    // Current table data is cleared.

    /**
     * Load high score table(uses load from file).
     * @param filename - file to read from.
     * @throws IOException - msg if failed opening file.
     */
    public void load(File filename) throws IOException {

        HighScoresTable tableLoad = loadFromFile(filename);
        clear();
        try { // If table load went good, load the high score info.
            assert tableLoad != null;
            arrayScoreInfo = (ArrayList<ScoreInfo>) tableLoad.getHighScores();
        } catch (Exception e) {
            System.out.print("Problem with loading the file");
        }
    }
}