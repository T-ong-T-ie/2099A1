package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A rose that hurts nearby actors
 */
public class Bloodrose extends Plant {

    /**
     * Constructor
     */
    public Bloodrose() {
        super("Bloodrose", 'w');
    }

    // Fix for Bloodrose.java
    @Override
    protected void applyEffect(Location location) {
        // Damage surrounding actors
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor target = destination.getActor();
                boolean wasAlive = target.isConscious();
                target.hurt(10);

                // Only show death message for player (Farmer)
                if (wasAlive && !target.isConscious() && target instanceof Player) {
                    System.out.println(FancyMessage.YOU_DIED);
                }
            }
        }

        // Same check for actor at this location
        if (location.containsAnActor()) {
            Actor target = location.getActor();
            boolean wasAlive = target.isConscious();
            target.hurt(10);

            if (wasAlive && !target.isConscious() && target instanceof Player) {
                System.out.println(FancyMessage.YOU_DIED);
            }
        }
    }
}