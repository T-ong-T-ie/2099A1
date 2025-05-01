package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

    private final Actor target;

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public AttackAction(Actor target) {
        this.target = target;
    }

    /**
     * Perform the attack action.
     *
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return a description of what happened that can be displayed to the user
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player) {
            Player player = (Player) actor;

            // Check if player has enough stamina to attack (minimum 15)
            if (player.getAttribute(BaseActorAttributes.STAMINA) < 15) {
                return actor + " is too tired to attack!";
            }

            // Apply stamina cost for attack
            player.applyAttackStaminaCost();

            WeaponItem equippedWeapon = player.getEquippedWeapon();
            if (equippedWeapon != null) {
                return equippedWeapon.attack(actor, target, map);
            } else {
                return actor.getIntrinsicWeapon().attack(actor, target, map);
            }
        } else {
            // Non-player actors don't have stamina restrictions
            if (actor.getIntrinsicWeapon() != null) {
                return actor.getIntrinsicWeapon().attack(actor, target, map);
            } else {
                // Fallback to a basic attack if no intrinsic weapon
                target.hurt(25);
                return actor + " attacks " + target + " for 25 damage";
            }
        }
    }

    /**
     * Returns a descriptive string for the action menu
     *
     * @param actor The actor performing the action
     * @return a description used for the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target;
    }
}