package menu;

/**
 * Menu Selection class.
 * @param <T> - type of return from the selection.
 */
public class MenuSelectionInfo<T> {

    private String keyName; // Key of the select(to press).
    private String selectMessage; // Message to present.
    private T returnType; // Type to return.
    private Menu<T> returnTypeMenu; // Menu return if it is submenu.

    /**
     * Construct selection info.
     * @param key - Key of the select(to press).
     * @param message - Message to present.
     * @param returnVal - value returned.
     */
    public MenuSelectionInfo(String key, String message, T returnVal) {
        this.keyName = key;
        this.selectMessage = message;
        this.returnType = returnVal;
    }

    /**
     * Constructor for menu info.
     * @param key - Key of the select(to press).
     * @param message - Message to present.
     * @param returnVal - menu returned.
     */
    public MenuSelectionInfo(String key, String message, Menu<T> returnVal) {
        this.keyName = key;
        this.selectMessage = message;
        this.returnTypeMenu = returnVal;
    }

    /**
     * Return the type.
     * @return - return the type.
     */
    public T getReturnType() {
        return returnType;
    }
    /**
     * Return the menu.
     * @return - return the menu.
     */
    public Menu<T> getReturnTypeMenu() {
        return returnTypeMenu;
    }

    /**
     * Return key name.
     * @return - string of key.
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Return massage of the select.
     * @return - string of massage.
     */
    public String getSelectMessage() {
        return selectMessage;
    }

}
