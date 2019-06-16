package levels;

import gameplay.Block;
import geometry.Point;
import java.awt.Color;
import java.awt.Image;
import java.util.Map;

/**
 * The symbol creator(a block creator).
 * Use a specific key to create a specific block.
 */
public class BlockCreatorSymbol implements BlockCreator {

    private double width;
    private double height;
    private int hitPoint;
    private Map<Integer, Color> colorFillMap; // Map for colors(fill-k).
    private Map<Integer, Image> imageFillMap; // Map for Image(fill-k).
    private Color stroke;

    /**
     * Construct the block creator (specific).
     * @param addWidth - width of block.
     * @param addHeight - height of block.
     * @param addHitPoint - hit points of block.
     * @param addColorFillMap - fill-k for color(number = color).
     * @param addImageFillMap - fill-k for image(number = image).
     * @param addStroke - stroke for block(can be null, means no stroke).
     */
    public BlockCreatorSymbol(double addWidth, double addHeight, int addHitPoint
            , Map<Integer, Color> addColorFillMap
            , Map<Integer, Image> addImageFillMap, Color addStroke) {
        this.width = addWidth;
        this.height = addHeight;
        this.hitPoint = addHitPoint;
        this.colorFillMap = addColorFillMap; // Map for colors(fill-k).
        this.stroke = addStroke;
        this.imageFillMap = addImageFillMap; // Map for Image(fill-k).
    }


    /**
     * Create block with the specific detail of the class in a location.
     *
     * @param xpos - x axis
     * @param ypos - y axis
     * @return - new block with the new information.
     */
    @Override
    public Block create(int xpos, int ypos) {
        Block block = new Block(new Point(xpos, ypos), width, height);
        block.setCountHits(hitPoint);

        if (stroke != null) {
            block.setStroke(stroke); // If stroke exist.
        }
        for (int i = 1; i <= hitPoint; i++) { // Check for both maps if exist.
            if (colorFillMap.get(i) != null) {
                block.setBlockColor(i, colorFillMap.get(i));
            }
            if (imageFillMap.get(i) != null) {
                block.setBlockImage(i, imageFillMap.get(i));
            }
        }
        return block;
    }
}
