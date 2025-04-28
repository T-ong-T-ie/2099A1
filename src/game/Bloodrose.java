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

    @Override
    protected void applyEffect(Location location) {
        // Damage surrounding actors (including the current location)
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor target = destination.getActor();
                target.hurt(10);

                // If player dies, show death message
                if (!target.isConscious() && target.toString().equals("Farmer")) {
                    System.out.println("YOU DIED");
                }
            }
        }

        // Check the current location too
        if (location.containsAnActor()) {
            Actor target = location.getActor();
            target.hurt(10);

            // If player dies, show death message
            if (!target.isConscious() && target.toString().equals("Farmer")) {
                System.out.println("YOU DIED");
            }
        }
    }
}