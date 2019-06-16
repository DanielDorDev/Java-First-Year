package levels;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Level specification reader(read the level info).
 */
public class LevelSpecificationReader {


    private static final String START_LEVEL = "START_LEVEL"; // Start line.
    private static final String END_LEVEL = "END_LEVEL"; // End line.

    /**
     * Main reader, read the info from reader file and put it into linked list
     * Every link list member has array list of string with the level info.
     * Mean every linked list contain a level info.
     * After that use a convector to make it a level information.
     * @param reader - file reader to read from.
     * @return - Level information (how to build level).
     * @throws Exception - if failed throw(exp come from the failed operator).
     */
    public List<LevelInformation> fromReader(java.io.Reader reader)
            throws Exception {
        LinkedList<ArrayList<String>> levelsList
                = new LinkedList<>(textToLevelsList(reader));

        ArrayList<LevelInformation> levelInformationArray = new ArrayList<>();
        for (ArrayList<String> convertToMap : levelsList) {
            levelInformationArray.add(new LevelFromText(convertToMap));
        }
        return levelInformationArray;
    }


    /**
     * Convert reader text to Linked list with levels.
     * @param reader - reader to read from.
     * @return - linked list of levels within information about the level.
     * @throws Exception -  if failed reading.
     */
    private static LinkedList<ArrayList<String>> textToLevelsList(
            java.io.Reader reader) throws Exception {

        BufferedReader buffReader = new BufferedReader(reader);
        LinkedList<ArrayList<String>> linkedGameString = new LinkedList<>();
        String tempString;
        while ((tempString = buffReader.readLine()) != null) {

            if (tempString.startsWith(START_LEVEL)) {
                ArrayList<String> arrayListLevel = new ArrayList<>();

                while (!(tempString.startsWith(END_LEVEL))) {
                    tempString = buffReader.readLine();
                    // Don't copy # or empty lines.
                    if (!(tempString.startsWith("#") || tempString.isEmpty())
                            && !tempString.startsWith(END_LEVEL)) {
                        arrayListLevel.add(tempString);
                    }

                }
                if (tempString.startsWith(END_LEVEL)) {
                    linkedGameString.add(arrayListLevel);
                }
            }
        }
        try {
            reader.close();

        } catch (Exception e) {
            System.err.print("Failed closing" + reader);
        }
        return linkedGameString;
    }
}
