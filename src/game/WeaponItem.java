package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.*;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

import java.util.Random;

/**
 * Class representing items that can be used as a weapon.
 * @author Adrian Kristanto
 */
public abstract class WeaponItem extends Item implements Weapon {
    private final int damage;
    private final String verb;
    private final int hitRate;

    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, true);
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
    }

    public int damage() {
        return damage;
    }

    public String verb() {
        return verb;
    }

    public int chanceToHit() {
        return hitRate;
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) < this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);
        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}