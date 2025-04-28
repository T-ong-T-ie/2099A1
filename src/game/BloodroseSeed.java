package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
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

        // Damage the farmer when planting
        actor.hurt(5);

        String result = actor + " planted a Bloodrose, taking 5 damage";

        // Check if the farmer died from planting
        if (!actor.isConscious()) {
            result += "\nYOU DIED";
        }

        return result;
    }
}