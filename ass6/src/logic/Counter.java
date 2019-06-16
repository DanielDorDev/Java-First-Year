package logic;

/**
 * Class of counter.
 */
public class Counter {

    private Integer value;

    /**
     * Construct counter with value.
     * @param valueStart - value to set.
     */
    public Counter(int valueStart) {
        this.value = valueStart;
    }

    /**
     * Add number to current count.
     *
     * @param number - int to add.
     */
    public void increase(int number) {
        this.value = this.value + number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number - int to subtract.
     */
    public void decrease(int number) {
        this.value = this.value - number;
    }

    /**
     * Return the value of the count.
     *
     * @return - int represent count.
     */
    public Integer getValue() {
        return this.value;
    }
}
