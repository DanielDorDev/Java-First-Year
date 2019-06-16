package menu;

/**
 * Exit the program.
 */
public class ExitTask implements Task<Void> {

    /**
     * Run the Class(means exit the game).
     * Later use to put massages when exit.
     *
     * @return - null for the Void(noting).
     */
    public final Void run() {
        System.exit(0);
        return null;
    }
}
