package game;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A talisman weapon that can be used to attack enemies and cure rot.
 */
public class Talisman extends WeaponItem {
    /**
     * Constructor for Talisman.
     */
    public Talisman() {
        super("Talisman", 'o', 0, "blesses", 100);
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        Location actorLocation = map.locationOf(owner);

        // Add cure actions for all adjacent locations
        for (Exit exit : actorLocation.getExits()) {
            Location destination = exit.getDestination();

            // Check if destination has something curable (ground or actor)
            if ((destination.getGround() instanceof CurableGround &&
                    ((CurableGround)destination.getGround()).isCurable()) ||
                    (destination.containsAnActor() && destination.getActor() instanceof Rottable &&
                            ((Rottable)destination.getActor()).isCurable())) {

                actions.add(new TalismanCureAction(destination));
            }
        }

        // Check the current location too
        if ((actorLocation.getGround() instanceof CurableGround &&
                ((CurableGround)actorLocation.getGround()).isCurable()) ||
                (actorLocation.containsAnActor() && actorLocation.getActor() instanceof Rottable &&
                        ((Rottable)actorLocation.getActor()).isCurable())) {

            actions.add(new TalismanCureAction(actorLocation));
        }

        return actions;
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        // Apply stamina cost if attacker is a player
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            if (!canAttack(player)) {
                return attacker + " is too tired to attack with Talisman!";
            }
            applyStaminaCost(player);
        }

        return super.attack(attacker, target, map);
    }

    /**
     * Check if the actor has enough stamina to attack with Talisman.
     * @param actor The actor using the Talisman
     * @return true if stamina >= 20, false otherwise
     */
    public boolean canAttack(Actor actor) {
        Integer stamina = actor.getAttribute(BaseActorAttributes.STAMINA);
        return stamina != null && stamina >= 20;
    }

    /**
     * Apply stamina cost when attacking.
     * @param actor The actor using the Talisman
     */
    public void applyStaminaCost(Actor actor) {
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 20);
    }
}