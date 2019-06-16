package menu;

/**
 * Task interface, object can be a task and have a run operator.
 * @param <T> - type of task(A object).
 */
public interface Task<T> {
    /**
     * Run task.
     * @return - info or noting depend on task.
     */
    T run();
}