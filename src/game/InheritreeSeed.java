package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A seed that grows into an Inheritree
 */
public class InheritreeSeed extends Seed {

    /**
     * Constructor
     */
    public InheritreeSeed() {
        super("Inheritree Seed");
    }

    @Override
    public String plant(Location location, Actor actor) {
        // Plant the Inheritree
        location.addItem(new Inheritree());

        // Convert surrounding blight to soil
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround() instanceof Blight) {
                destination.setGround(new Soil());
            }
        }

        return actor + " planted an Inheritree, converting surrounding blight to soil";
    }
}