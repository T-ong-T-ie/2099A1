package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

import java.util.Random;

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
        IntrinsicWeapon weapon = actor.getIntrinsicWeapon();
        if (weapon == null) {
            return actor + " has no weapon to attack " + target;
        }

        // Hardcode BareFist properties (damage=25, hitRate=50, verb="punches")
        int damage = 25;
        int hitRate = 50;
        String verb = "punches";
        float damageMultiplier = 1.0f; // Default multiplier from Actor

        String result = actor + " " + verb + " " + target;
        if (new Random().nextInt(100) < hitRate) {
            int finalDamage = Math.round(damage * damageMultiplier);
            target.hurt(finalDamage);
            result += " for " + finalDamage + " damage";
            if (!target.isConscious()) {
                map.removeActor(target);
                result += "\n" + target + " is killed";
            }
        } else {
            result += " and misses";
        }
        return result;
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