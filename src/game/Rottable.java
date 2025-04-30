package game;

/**
 * Interface for actors that can be affected by rot
 */
public interface Rottable {
    /**
     * Get the current rot timer value
     * @return the current value of the rot timer
     */
    int getRotTimer();

    /**
     * Set the rot timer to a specific value
     * @param time the new value for the rot timer
     */
    void setRotTimer(int time);

    /**
     * Decrease the rot timer by 1 and check if it has expired
     * @return true if timer has reached 0 or below, false otherwise
     */
    boolean decrementRotTimer();

    /**
     * Reset the rot timer to its initial value
     */
    void resetRotTimer();

    /**
     * Check if this rottable entity can be cured
     * @return true if curable, false otherwise
     */
    boolean isCurable();
}