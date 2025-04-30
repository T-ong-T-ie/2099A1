package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A seed that grows into a Bloodrose
 */
public class BloodroseSeed extends Seed {

    /**
     * Constructor
     */
    public BloodroseSeed() {
        super("Bloodrose Seed");
    }

    @Override
    public String plant(Location location, Actor actor) {
        // Plant the Bloodrose
        location.addItem(new Bloodrose());

        return actor + " planted a Bloodrose";
    }
}