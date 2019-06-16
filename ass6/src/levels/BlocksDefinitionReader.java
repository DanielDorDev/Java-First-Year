package levels;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Block reader(read the specific block data and return Symbol factory).
 */
public class BlocksDefinitionReader {

    /**
     * Read data from the block specification and convert it to factory block.
     * @param reader - a file reader.
     * @return - Block factory object(can create blocks).
     * @throws Exception - if failed print it.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader)
            throws Exception {
        // Array for the data string.
        ArrayList<String> arrayDefinition = new ArrayList<>();

        try { // Create reader to go on the file and read all text(no # or null)
            BufferedReader buffReader = new BufferedReader(reader);
            String tempString;
            while ((tempString = buffReader.readLine()) != null) {
                if (tempString.startsWith("#") || tempString.isEmpty()) {
                    continue;
                }
                arrayDefinition.add(tempString);
            }
        } catch (Exception e) {
            throw new Exception("Failed to read the definition file");
        }
        return new BlocksFromSymbolsFactory(arrayDefinition);

        }
}