package levels;
import geometry.Velocity;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static geometry.Velocity.fromAngleAndSpeed;

/**
 * Class with function for reading txt.
 * I made it this way so it will be changeable, easy access and understanding.
 * I go over all the level array(no order).
 */
public class LevelReader {
    /**
     * Read the level name.
     * @param array - array to go by and find the level name.
     * @return - name level singular.
     * @throws Exception - fail to read level name.
     */
    public String readLevelName(ArrayList<String> array) throws Exception {
        final String levelName = "level_name:";
        for (String check : array) {
            if (check.startsWith(levelName)) {
                return check.substring(levelName.length());
            }

        }
        throw new Exception("Missing level name");
    }

    /**
     * Read the velocity.
     * @param array - level data to read from.
     * @return - velocity array.
     * @throws Exception - if failed reading ball velocity.
     */
    public ArrayList<Velocity> readBallVelocity(ArrayList<String> array)
            throws Exception {
        final String velocityStart = "ball_velocities:";
        final ArrayList<Velocity> velocityArrayList = new ArrayList<>();
        for (String check : array) {
            if (check.startsWith(velocityStart)) {
                String[] velocityArrString = check.substring(
                        velocityStart.length()).split(" ");
                for (String velocString : velocityArrString) {
                    String[] angleSpeed = velocString.split(",");
                    velocityArrayList.add(
                            fromAngleAndSpeed(Integer.parseInt(angleSpeed[0])
                                    , Integer.parseInt(angleSpeed[1])));

                }
                if (velocityArrayList.isEmpty()) {
                    throw new Exception("Empty velocity list");
                } else {
                    return velocityArrayList;
                }
            }
        }
        throw new Exception("Missing ball velocity");

    }

    /**
     * Image reader.
     * @param array - level data to read from.
     * @return - image if found.
     * @throws Exception - if no image found.
     */
    public Image imageReader(ArrayList<String> array) throws  Exception {
        final String startWith = "image(";

        final String endWith = ")";
        Image imageBoard = null;

        for (String check : array) {
            if (check.contains(startWith)) {

                try {
                    InputStream is = ClassLoader.getSystemClassLoader(
                    ).getResourceAsStream(check.substring(
                            check.indexOf(startWith) + startWith.length()
                            , check.lastIndexOf(endWith)));

                    imageBoard = ImageIO.read(is);
                    return imageBoard;
                } catch (IOException e) {
                    throw new Exception("Cannot open background image IO");
                } finally {
                    try {
                        if (imageBoard != null) {
                            imageBoard.flush();
                        }
                    } catch (Exception e) {
                        System.out.print("Failed to flush background");
                    }
                }
            }
        }
        return null;

    }

    /**
     * Color reader(use ColorParser).
     * @param array - level data to read from.
     * @return - color if found.
     * @throws Exception - if failed error msg.
     */
    public Color colorReader(ArrayList<String> array) throws Exception {
        final String startWith = "background:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                return new ColorsParser().colorFromString(
                        check.substring(startWith.length()));
            }
            }
        throw new Exception("No Image and Color");
    }

    /**
     * Read paddle speed.
     * @param array - array data to read from.
     * @return - int with paddle speed if found.
     * @throws Exception - msg if failed.
     */
    public int paddleSpeedReader(ArrayList<String> array) throws Exception {
        final String startWith = "paddle_speed:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return Integer.parseInt(check.substring(
                            startWith.length()));
                } catch (Exception e) {
                    System.out.println("Wrong format for paddle speed");
                }
            }
        }
        throw new Exception("Missing paddle speed");
    }
    /**
     * Read paddle width.
     * @param array - array data to read from.
     * @return - int with paddle width if found.
     * @throws Exception - msg if failed.
     */
    public int paddleWidthReader(ArrayList<String> array) throws Exception {
        final String startWith = "paddle_width:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return Integer.parseInt(check.substring(startWith.length()));
                } catch (Exception e) {
                    System.out.println("Wrong format for paddle width");
                }
            }
        }
        throw new Exception("Missing paddle width");
    }

    /**
     * Read start block x .
     * @param array - array data to read from.
     * @return - int with x position if found.
     * @throws Exception - msg if failed.
     */
    public int blockStartXReader(ArrayList<String> array) throws Exception {
        final String startWith = "blocks_start_x:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return Integer.parseInt(check.substring(startWith.length()));
                } catch (Exception e) {
                    System.out.println("Wrong format for block start x");
                }
            }
        }
        throw new Exception("Missing block start x");
    }
    /**
     * Read start block y .
     * @param array - array data to read from.
     * @return - int with y position if found.
     * @throws Exception - msg if failed.
     */
    public int blockStartYReader(ArrayList<String> array) throws Exception {
        final String startWith = "blocks_start_y:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return Integer.parseInt(check.substring(startWith.length()));
                } catch (Exception e) {
                    System.out.println("Wrong format for block start y");
                }
            }
        }
        throw new Exception("Missing block start y");
    }
    /**
     * Read row height .
     * @param array - array data to read from.
     * @return - int with row height if found.
     * @throws Exception - msg if failed.
     */
    public int rowHighReader(ArrayList<String> array) throws Exception {
        final String startWith = "row_height:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return Integer.parseInt(check.substring(startWith.length()));
                } catch (Exception e) {
                    System.out.println("Wrong format for block high");
                }
            }
        }
        throw new Exception("Missing block high");
    }

    /**
     * Number of blocks in the game.
     * @param array - data level to read from.
     * @return - int with block number.
     * @throws Exception - msg if failed.
     */
    public int blockNumberReader(ArrayList<String> array) throws Exception {
        final String startWith = "num_blocks:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return Integer.parseInt(check.substring(startWith.length()));
                } catch (Exception e) {
                    System.out.println("Wrong format for block number");
                }
            }
        }
        throw new Exception("Missing block number");
    }

    /**
     * Find block definition path.
     * @param array - data level to read from.
     * @return - String with path to block def.
     * @throws Exception - msg if failed.
     */
    public String blockDefReader(ArrayList<String> array) throws Exception {
        final String startWith = "block_definitions:";
        for (String check : array) {
            if (check.startsWith(startWith)) {
                try {
                    return check.substring(startWith.length());
                } catch (Exception e) {
                    System.out.println("Cannot find block definition path");
                }
            }
        }
        throw new Exception("Missing block definitions");
    }

    /**
     * Block lay out find.
     * @param array - data level to read from.
     * @return - Array string with layout info if found.
     * @throws Exception - msg if failed.
     */
    public ArrayList<String> blockLayOut(ArrayList<String> array)
            throws Exception {
        try {
            final String startWith = "START_BLOCKS";
            final String endWith = "END_BLOCKS";


            return new ArrayList<>(
                    array.subList(array.indexOf(startWith) + 1
                            , array.indexOf(endWith)));
        } catch (Exception e) {
            throw new Exception("Failed to create block layout array");
        }
    }
}
