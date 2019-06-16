package menu;
import animation.Animation;

/**
 * Interface for menu.
 * @param <T> - generic type to return value.
 */
public interface Menu<T> extends Animation {

    /**
     * Add key to menu.
     * @param key - key to wait for.
     * @param message - message to print.
     * @param returnVal - return generic type value.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Get status of the menu.
     * @return - return generic type value.
     */
    T getStatus();

    /**
     * Add sub menu to a menu.
     * @param key - key(for press).
     * @param message - message in the menu.
     * @param subMenu - the menu to add (sub menu).
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * Reset data(status and should stop).
     */
    void resetData();
}