package game;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;


/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    private Actor target;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public AttackAction(Actor target) {
        this.target = target;
    }

    /**
     * Execute the attack action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player) {
            // Player can have an equipped weapon or use intrinsic weapon
            Player player = (Player) actor;
            WeaponItem equippedWeapon = player.getEquippedWeapon();

            // Apply stamina cost for attack (this method needs to be added)
            player.applyAttackStaminaCost();

            if (equippedWeapon != null) {
                // Use equipped weapon if available
                return equippedWeapon.attack(actor, target, map);
            } else {
                // Use intrinsic weapon otherwise
                return actor.getIntrinsicWeapon().attack(actor, target, map);
            }
        } else {
            // Non-player actors just use their intrinsic weapon
            return actor.getIntrinsicWeapon().attack(actor, target, map);
        }
    }

    /**
     * Returns a descriptive string for the menu.
     *
     * @param actor The actor performing the action.
     * @return a String describing the action, suitable for displaying in the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target;
    }
}