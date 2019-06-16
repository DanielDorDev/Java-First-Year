package levels;

import java.awt.Color;

/**
 * Color parser(reader).
 */
public class ColorsParser {

    private static final String STR_COLOR = "color(";
    private static final String END_COLOR = ")";
    private static final String STR_FROM = "(";
    private static final String STR_RGB = "color(RGB(";
    private static final String END_RGB = "))";
    /**
     * Parse color definition and return the specified color.
     *
     * @param s - color to read already in format(need to be).
     * @return - color as asked in string(default black).
     * @throws Exception - if wrong format\ color\ RGB.
     */
    public java.awt.Color colorFromString(String s) throws Exception {


        if (s.startsWith(STR_RGB)) { // RGB color.
            try {

                String[] xyz = s.substring((s.lastIndexOf(STR_FROM) + 1)
                        , s.indexOf(END_RGB)).split(",");
                return new Color(Integer.parseInt(xyz[0])
                        , Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
            } catch (Exception e) { // If failed
                System.out.println("Can't convert RGB, put default");
                return Color.BLACK;
            }

        } else if (s.startsWith(STR_COLOR)) { // Regular color object.
            try {
                String trimmed = s.substring((s.indexOf(STR_FROM) + 1)
                        , s.indexOf(END_COLOR));
                return (Color) Color.class.getField(trimmed).get(null);
            } catch (Exception e) { // If failed
                System.out.println("Can't convert color, put default");
            }
        }
        throw new Exception("Wrong format of color: " + s);
    }
}