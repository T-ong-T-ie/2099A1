package game;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * Interface for ground that can be cured
 */
public interface CurableGround {
    /**
     * Returns the ground that replaces this ground when cured
     * @return the cured version of this ground
     */
    Ground cure();

    /**
     * Check if this ground can be cured
     * @return true if curable, false otherwise
     */
    boolean isCurable();
}