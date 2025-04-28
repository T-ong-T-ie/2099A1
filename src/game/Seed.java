package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Base class for all seeds that can be planted
 */
public abstract class Seed extends Item {

    /**
     * Constructor.
     *
     * @param name the name of this seed
     */
    public Seed(String name) {
        super(name, '*', true);
    }

    /**
     * Creates a PlantAction for this seed
     *
     * @param actor the actor carrying this seed
     * @return a list containing the PlantAction
     */
    @Override
    public ActionList allowableActions(Actor actor, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new PlantAction(this));
        return actions;
    }

    /**
     * Creates the plant from this seed at the given location
     *
     * @param location the location to plant at
     * @param actor the actor doing the planting
     * @return a string describing what happened
     */
    public abstract String plant(Location location, Actor actor);
}