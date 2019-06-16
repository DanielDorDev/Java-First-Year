package animation;

import biuoop.DrawSurface;
import geometry.Point;

import java.awt.Color;

/**
 * Class for printing text fill.
 */
public class TextFill {
    /**
     * Construct the printing class for wrap painting.
     * @param d - surface to draw on.
     * @param stringPrint - string to print.
     * @param size - size of font.
     * @param base - base color(inside).
     * @param wrap - wrap color(outside).
     * @param point - point start.
     */
    public void printText(DrawSurface d, String stringPrint, int size
            , Color base, Color wrap, Point point) {
        d.setColor(wrap);
        d.drawText((int) point.getX() + 1, (int) point.getY()
                , stringPrint, size);
        d.drawText((int) point.getX() - 1, (int) point.getY()
                , stringPrint, size);
        d.drawText((int) point.getX(), (int) point.getY() + 1
                , stringPrint, size);
        d.drawText((int) point.getX(), (int) point.getY() - 1
                , stringPrint, size);

        d.setColor(base);
        d.drawText((int) point.getX(), (int) point.getY()
                , stringPrint, size);
    }
}
