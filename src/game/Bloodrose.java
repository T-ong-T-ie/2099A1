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
        // Damage surrounding actors (including the current location)
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor target = destination.getActor();
                boolean wasAlive = target.isConscious();
                target.hurt(10);

                // Only print death message once when transitioning from alive to dead
                if (wasAlive && !target.isConscious() && target.toString().equals("Farmer")) {
                    System.out.println(FancyMessage.YOU_DIED);
                }
            }
        }

        // Check the current location too
        if (location.containsAnActor()) {
            Actor target = location.getActor();
            boolean wasAlive = target.isConscious();
            target.hurt(10);

            // Only print death message once when transitioning from alive to dead
            if (wasAlive && !target.isConscious() && target.toString().equals("Farmer")) {
                System.out.println(FancyMessage.YOU_DIED);
            }
        }
    }
}