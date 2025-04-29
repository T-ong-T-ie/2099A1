package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Action to cure rot at a location using a Talisman
 */
public class TalismanCureAction extends Action {
    private final Location targetLocation;

    /**
     * Constructor
     * @param targetLocation location to cure
     */
    public TalismanCureAction(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Check if actor has enough stamina (20 stamina required)
        if (actor instanceof Player) {
            Player player = (Player) actor;
            if (player.getAttribute(BaseActorAttributes.STAMINA) < 20) {
                return actor + " is too tired to use Talisman!";
            }

            // Apply stamina cost
            player.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 20);
        }

        boolean curedSomething = false;

        // Cure ground
        if (targetLocation.getGround() instanceof CurableGround) {
            CurableGround curableGround = (CurableGround) targetLocation.getGround();
            if (curableGround.isCurable()) {
                targetLocation.setGround(curableGround.cure());
                curedSomething = true;
            }
        }

        // Cure actor
        if (targetLocation.containsAnActor() && targetLocation.getActor() instanceof Rottable) {
            Rottable rottableActor = (Rottable) targetLocation.getActor();
            if (rottableActor.isCurable()) {
                rottableActor.resetRotTimer();
                curedSomething = true;
                return actor + " uses Talisman to cleanse " + targetLocation.getActor();
            }
        }

        if (curedSomething) {
            return actor + " uses Talisman to cure at " + targetLocation.getGround();
        }

        return actor + " used Talisman but nothing happened";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses Talisman to cure at " + targetLocation.getGround();
    }
}