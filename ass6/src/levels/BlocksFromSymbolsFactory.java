package levels;

import gameplay.Block;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Arrays;


/**
 * A block factory, can create block by given symbol.
 */
public class BlocksFromSymbolsFactory {

    private static final String SDEF = "sdef "; // Spacer definition.
    private static final String BDEF = "bdef "; // Block definition.
    private static final String SYMBOL = "symbol:"; // A symbol fr block.
    private static final String DEFAULT = "default"; // Default definition.
    private static final String HEIGHT = "height:"; // Height of block.
    private static final String WIDTH = "width:"; // Width of the block
    private static final String HITPOINTS = "hit_points:"; // HP of block.
    private static final String STROKE = "stroke:"; // Stroke for block.
    private static final String FILL = "fill"; // Fill (color or image).
    private static final String IMAGE = "image("; // Image for block.

    // Spacer and block creator maps.
    private Map<String, Integer> spacerWidths = new HashMap<>();
    private Map<String, BlockCreator> blockCreators = new HashMap<>();

    /**
     * Construct the symbol factory.
     *
     * @param array - given definition array.
     * @throws Exception - if failed in the task(in creating).
     */
    public BlocksFromSymbolsFactory(ArrayList<String> array) throws Exception {
        blockCreatorConstruct(getBlockCreatorStrArray(array));
    }

    /**
     * @param array - with text.
     * @return - Array list of block definition after split it to comfort.
     * @throws Exception - no format of block definition found.
     */
    private ArrayList<ArrayList<String>>
    getBlockCreatorStrArray(ArrayList<String> array)
            throws Exception {
        ArrayList<String> def = new ArrayList<>();
        ArrayList<String> bdef = new ArrayList<>();

        for (String check : array) {
            if (check.startsWith(DEFAULT)) { // If it is default.
                def.add(check.substring(DEFAULT.length()));
            } else if (check.startsWith(BDEF + SYMBOL)) { // If block def.
                bdef.add(check.substring((BDEF).length()));
            } else if (check.startsWith((SDEF + SYMBOL))) { // If space def.
                addSpacer(check);
            }
        } // Must be bdef(because how will we know how to put them).
        if (bdef.isEmpty()) {
            throw new Exception(
                    "No format for bdef files, empty surface");

        }
        ArrayList<ArrayList<String>> blockFinalStringCreator
                = new ArrayList<>();

        for (String lineConvert : bdef) {
            // Combine the bdef and the def using set.
            // And it is comfortable way to create the final data for block.
            HashSet<String> setString
                    = new HashSet<>(Arrays.asList(lineConvert.split(" ")));

            for (String defPut : def) {
                setString.addAll(Arrays.asList(defPut.split(" ")));
            }
            blockFinalStringCreator.add(new ArrayList<>(setString));
        }

        return blockFinalStringCreator;

    }

    /**
     * Construct block creator(add all info together).
     * @param array - data info for block.
     * @throws Exception - if missing data or colors.
     */
    public void blockCreatorConstruct(ArrayList<ArrayList<String>> array)
            throws Exception {

        for (ArrayList<String> arrayMember : array) {

            // Block info set to null.
            String symbol = null;
            Map<Integer, Image> fillImage = new HashMap<>();
            Integer width = null, height = null, hp = null;
            Color stroke = null;
            Map<Integer, Color> fillColor = new HashMap<>();
            ColorsParser cc = new ColorsParser();

            // If "" string.
            for (String member : arrayMember) {
                if (member.isEmpty()) {
                    continue;
                }
                // Substring to details and info convert.
                if (member.startsWith(WIDTH)) {
                    width = Integer.parseInt(member.substring(WIDTH.length()));
                } else if (member.startsWith(HEIGHT)) {
                    height = Integer.parseInt(member.substring(
                            HEIGHT.length()));
                } else if (member.startsWith(HITPOINTS)) {
                    hp = Integer.parseInt(member.substring(
                            HITPOINTS.length()));
                } else if (member.startsWith(STROKE)) {
                    stroke = cc.colorFromString(member.substring(
                            STROKE.length()));
                } else if (member.startsWith(SYMBOL)) {
                    symbol = member.substring(SYMBOL.length());
                } else { // Color option.
                    if (member.startsWith(FILL + "-")) { // Starts with fill-k
                        // If image.
                        if (member.contains(IMAGE)) {
                            ArrayList<String> convertToArr = new ArrayList<>();
                            convertToArr.add(member.substring((
                                    FILL + ":").length()));
                            fillImage.put(Integer.parseInt(member.substring(
                                    member.indexOf("-") + 1
                                    , member.indexOf(":")))
                                    , new LevelReader().imageReader(
                                            convertToArr));
                        // If color.
                        } else {
                            fillColor.put(Integer.parseInt(member.substring(
                                    member.indexOf("-") + 1
                                    , member.indexOf(":"))),
                                    cc.colorFromString(
                                            member.substring(
                                                    member.indexOf(":") + 1)));
                        }
                    } else { // Start with fill:color( or fill:image(.
                        if (member.contains(IMAGE)) { // If image.
                            ArrayList<String> convertToArr
                                    = new ArrayList<>();
                            convertToArr.add(member.substring((
                                    FILL + ":").length()));
                            fillImage.put(1, new LevelReader(
                            ).imageReader(convertToArr));

                        } else { // If color.
                            fillColor.put(1, cc.colorFromString(
                                    member.substring((FILL + ":").length())));
                        }
                    }
                }
            }
            try { // Check if color exist(no color seem bad block.
                if (fillImage.isEmpty() && fillColor.isEmpty()) {
                    throw new Exception("Empty color");
                /*
                Of course i could make a only stroke "color", or not visible
                for not it seem bit too boring.
                 */
                }
                this.blockCreators.put(symbol, // Create block creator.
                        new BlockCreatorSymbol(width, height, hp, fillColor
                                , fillImage, stroke));
                // It is ok that member may product exp, will catch it.
            } catch (Exception e) { // If failed and missing data.
                throw new Exception("Missing data for block creator " + e);
            }
        }
    }

    /**
     * Add spacer to map.
     * @param check - sdef string to convert.
     * @throws Exception - if wrong format.
     */
    public void addSpacer(String check) throws Exception {
        String[] checkSplit = check.substring((
                SDEF + SYMBOL).length()).split(" ");
        if (checkSplit[0].length() != 1) {
            throw new Exception("Wrong format for space char" + check);
        } // symbol-^:space info.
        spacerWidths.put((checkSplit[0]), Integer.parseInt(
                checkSplit[1].substring(WIDTH.length())));
    }

    /**
     * Returns true if 's' is a valid space symbol.
     * @param s - string to check if symbol.
     * @return - true if it is symbol spacer.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.get(s) != null;
    }
    /**
     * Returns true if 's' is a valid block symbol.
     * @param s - string to check if symbol.
     * @return - true if it is symbol block.
     */
    // returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.get(s) != null;
    }

    /**
     * Get spacer width by symbol.
     * @param s - symbol of the spacer.
     * @return - the width of spacer.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Get the block by using key.
     * @param s - key of map(what creator to use).
     * @param x - x position.
     * @param y - y position.
     * @return - symbol s. The block will be located at position (x, y).
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }
}