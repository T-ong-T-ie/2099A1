package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Action to plant a seed at the current location
 */
public class PlantAction extends Action {
    private final Seed seed;

    /**
     * Constructor.
     *
     * @param seed the seed to plant
     */
    public PlantAction(Seed seed) {
        this.seed = seed;
    }

    /**
     * Plant the seed at the actor's location if it's valid soil
     *
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return a description of what happened
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        // Check if the ground is soil
        if (!(location.getGround() instanceof Soil)) {
            return actor + " cannot plant on " + location.getGround();
        }

        // Remove the seed from inventory
        actor.removeItemFromInventory(seed);

        // Plant the seed and return the result
        return seed.plant(location, actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " plants " + seed;
    }
}