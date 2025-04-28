package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Base class for all plants
 */
public abstract class Plant extends Item {

    /**
     * Constructor.
     *
     * @param name the name of this plant
     * @param displayChar the character to display for this plant
     */
    public Plant(String name, char displayChar) {
        super(name, displayChar, false);
    }

    /**
     * Apply the plant's effect each turn
     */
    @Override
    public void tick(Location currentLocation) {
        applyEffect(currentLocation);
    }

    /**
     * Apply the plant's effect to surrounding actors
     *
     * @param location the location of the plant
     */
    protected abstract void applyEffect(Location location);
}