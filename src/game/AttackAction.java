package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;


/**
 * An action to attack another actor with the attacker's intrinsic weapon.
 */
public class AttackAction extends Action {
    private Actor target;

    /**
     * Constructor.
     * @param target The actor to attack
     */
    public AttackAction(Actor target) {
        this.target = target;
    }

    /**
     * Executes the attack, applying damage based on the weapon's hit chance.
     * @param actor The actor performing the attack
     * @param map   The game map
     * @return A description of the attack outcome
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Weapon weapon;

        // Check if actor is Player (which has getWeapon method)
        if (actor instanceof Player) {
            weapon = ((Player) actor).getWeapon();
        } else {
            // For other actors, use intrinsic weapon
            weapon = actor.getIntrinsicWeapon();
        }

        if (weapon == null) {
            return actor + " has no weapon to attack " + target;
        }

        // Apply stamina cost if using Talisman
        if (actor instanceof Player) {
            ((Player) actor).applyAttackStaminaCost();
        }

        return weapon.attack(actor, target, map);
    }

    /**
     * Describes the attack action for the menu.
     * @param actor The actor performing the attack
     * @return The menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target;
    }
}