package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A tree that heals nearby actors
 */
public class Inheritree extends Plant {

    /**
     * Constructor
     */
    public Inheritree() {
        super("Inheritree", 't');
    }

    @Override
    protected void applyEffect(Location location) {
        // Heal surrounding actors (including the current location)
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor target = destination.getActor();
                target.heal(5);

                // Also restore stamina if actor has it
                if (target.hasAttribute(BaseActorAttributes.STAMINA)) {
                    target.modifyAttribute(BaseActorAttributes.STAMINA,
                            ActorAttributeOperations.INCREASE, 5);
                }
            }
        }

        // Check the current location too
        if (location.containsAnActor()) {
            Actor target = location.getActor();
            target.heal(5);

            if (target.hasAttribute(BaseActorAttributes.STAMINA)) {
                target.modifyAttribute(BaseActorAttributes.STAMINA,
                        ActorAttributeOperations.INCREASE, 5);
            }
        }
    }
}