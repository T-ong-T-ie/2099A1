package game;

/**
 * Interface for entities affected by rot
 */
public interface Rottable {
    /**
     * Get the current rot timer value
     * @return number of turns remaining before rot takes effect
     */
    int getRotTimer();

    /**
     * Set the rot timer to a specific value
     * @param time the new timer value
     */
    void setRotTimer(int time);

    /**
     * Decrement the rot timer by 1
     * @return true if timer reached zero, false otherwise
     */
    boolean decrementRotTimer();

    /**
     * Reset the rot timer to its initial value
     */
    void resetRotTimer();

    /**
     * Check if this entity can be cured of rot
     * @return true if curable, false otherwise
     */
    boolean isCurable();
}