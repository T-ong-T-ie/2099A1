package game;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto
 */
public class Blight extends Ground implements CurableGround {
    public Blight() {
        super('x', "Blight");
    }

    @Override
    public Ground cure() {
        return new Soil();
    }

    @Override
    public boolean isCurable() {
        return true;
    }
}