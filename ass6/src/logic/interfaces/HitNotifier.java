package logic.interfaces;

/**
 * Class for notify when hit happen.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl - hit listener to add.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl - hit listener to remove.
     */
    void removeHitListener(HitListener hl);
}