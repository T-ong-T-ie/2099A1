package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import java.util.Random;

/**
 * A talisman weapon that can be used to attack enemies.
 */
public class Talisman extends WeaponItem {
    /**
     * Constructor for Talisman.
     */
    public Talisman() {
        super("Talisman", 'T', 40, "blesses", 80);
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) < this.chanceToHit())) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(this.damage());
        String result = String.format("%s %s %s for %d damage", attacker, this.verb(), target, this.damage());
        if (!target.isConscious()) {
            map.removeActor(target);
            result += "\n" + target + " is killed";
        }
        return result;
    }

    /**
     * Check if the actor has enough stamina to attack with Talisman.
     * @param actor The actor using the Talisman
     * @return true if stamina >= 20, false otherwise
     */
    public boolean canAttack(Actor actor) {
        // Use BaseActorAttributes.STAMINA instead of String "STAMINA"
        Integer stamina = actor.getAttribute(BaseActorAttributes.STAMINA);
        return stamina != null && stamina >= 20;
    }

    /**
     * Apply stamina cost when attacking.
     * @param actor The actor using the Talisman
     */
    public void applyStaminaCost(Actor actor) {
        // Add the ActorAttributeOperations.DECREASE parameter
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 20);
    }

    @Override
    public String toString() {
        return "Talisman";
    }
}