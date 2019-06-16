package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import menu.Menu;
import menu.MenuSelectionInfo;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Animation for menu.
 * @param <T> - type of object to return.
 */
public class MenuAnimation<T> implements Menu<T> {

    private String massageTop; // Massage for the menu.
    private KeyboardSensor keyBoard;
    private T status; // Status = the task to make.
    private ArrayList<MenuSelectionInfo<T>> selectArray; // Operations in menu.
    private ArrayList<MenuSelectionInfo<T>> menuArray; // Another menus.
    private Image imageBoard;
    private AnimationRunner runner;
    private boolean stopGame; // Stop the animation.

    /**
     * Construct menu.
     *
     * @param run            - animation runner.
     * @param title          - massage for the menu.
     * @param keyboardSensor - key board sensor.
     */
    public MenuAnimation(AnimationRunner run,
                         String title, KeyboardSensor keyboardSensor) {
        this.massageTop = title;
        this.keyBoard = keyboardSensor;
        this.selectArray = new ArrayList<>();
        this.menuArray = new ArrayList<>();
        this.runner = run;
        setImageIcon(); // Set image.
    }

    /**
     * Make a frame.
     * <p>
     * Print it by how much operations there is.
     *
     * @param d  - surface that frame will be cast on.
     * @param dt - time passed.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        final int font = 50;
        if (imageBoard != null) {
            d.drawImage(0, 0, this.imageBoard);
        } else {
            d.setColor(Color.cyan);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }

        new TextFill().printText(d, this.massageTop, font,
                Color.ORANGE, Color.blue,
                new Point(d.getWidth() / 7, d.getHeight() / 7));

        int move = 0; // How much to move(go down in y axis).

        // For menu operations, print them and check if pressed.

        for (int i = 0; i < this.menuArray.size(); i++, move++) {
            new TextFill().printText(d, menuArray.get(i).getSelectMessage()
                    , font / 2, Color.white
                    , Color.black, new Point(d.getWidth() / 6
                            , d.getHeight() / 3 + move * font));
            if (keyBoard.isPressed(menuArray.get(i).getKeyName())) {
                Menu<T> menuRun = menuArray.get(i).getReturnTypeMenu();
                menuRun.resetData();
                runner.run(menuRun);
                this.status = menuRun.getStatus();
                stopGame = true; // stop the game and continue to operations.
                return;
            }
        }

        // For other operations, print them and check if pressed.

        for (int i = 0; i < this.selectArray.size(); i++, move++) {

            new TextFill().printText(d, selectArray.get(i).getSelectMessage()
                    , font / 2, Color.white
                    , Color.black, new Point(d.getWidth() / 6
                            , d.getHeight() / 3 + move * font));

            if (keyBoard.isPressed(selectArray.get(i).getKeyName())) {
                this.status = selectArray.get(i).getReturnType();
                stopGame = true; // stop the game and continue to operations
                return;
            }
        }
    }

    /**
     * Set Game image.
     */
    public void setImageIcon() {
        // Set image , read it from the folder and print it.

        try {
            InputStream is = ClassLoader.getSystemClassLoader(
            ).getResourceAsStream(
                    "background_images/mainPicture.jpeg");
            this.imageBoard = ImageIO.read(is);
            is.close();

        } catch (IOException e) {
            this.imageBoard = null;
        } finally {
            try {
                if (imageBoard != null) {
                    imageBoard.flush();
                }
            } catch (Exception e) {
                System.out.print("Failed to flush / close ");
            }
        }
    }

    /**
     * Get status of the menu.
     *
     * @return - return generic type value.
     */
    public T getStatus() {
        return status;
    }


    /**
     * Reset the stop and status members.
     */
    public void resetData() {
        this.status = null;
        this.stopGame = false;

    }

    /**
     * Stop the frame.
     *
     * @return - true or false(on\off).
     */
    @Override
    public boolean shouldStop() {
        return this.stopGame;
    }

    /**
     * Add key to menu.
     *
     * @param key       - key to wait for.
     * @param message   - message to print.
     * @param returnVal - return generic type value.
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selectArray.add(new MenuSelectionInfo<>(key, message, returnVal));
    }


    /**
     * Add key to menu.
     *
     * @param key     - key to wait for.
     * @param message - message to print.
     * @param menu    - return menu type value.
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> menu) {
        this.menuArray.add(new MenuSelectionInfo<>(key, message, menu));
    }
}
