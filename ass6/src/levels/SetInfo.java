package levels;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 * Set level info(main level set contain some levels, it will represent it).
 */
public class SetInfo {
    private ArrayList<String> key = new ArrayList<>(); // Keys(press them).
    private ArrayList<String> path = new ArrayList<>(); // Path to file.
    private ArrayList<String> name = new ArrayList<>(); // Name of set.

    /**
     * Construct info set.
     * Read the main set level and convert it to readable array.
     * Use 3 Array list(place (i) will represent a level).
     * @param source - a source to use(main level set).
     */
    public SetInfo(String source) {
            String tempString;
        InputStream is = ClassLoader.getSystemClassLoader(
        ).getResourceAsStream(source);
        try {
                LineNumberReader lineNum = new LineNumberReader(
                        new InputStreamReader(is));
                while ((tempString = lineNum.readLine()) != null) {
                    if (lineNum.getLineNumber() % 2 == 0) {
                        this.path.add(tempString);
                    } else {
                        String[] info = tempString.split(":");
                        this.key.add(info[0]);
                        this.name.add("(" + info[0] + ") " + info[1]);
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed reading a file: " + source);
            }

        }

    /**
     * Return the key in i place.
     * @param place - the i object in array.
     * @return - the information asked.
     */
    public String getKey(int place) {
        return key.get(place);
    }
    /**
     * Return the name of level in i place.
     * @param place - the i object in array.
     * @return - the information asked.
     */
    public String getName(int place) {
        return name.get(place);
    }
    /**
     * Return the path source in i place.
     * @param place - the i object in array.
     * @return - the information asked.
     */
    public String getPath(int place) {
        return path.get(place);
    }
    /**
     * Return the size of array(how much levels number).
     * @return - the information asked.
     */
    public int getSize() {
       return this.key.size();
    }
}
