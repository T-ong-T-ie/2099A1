package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.*;

/**
 * A behaviour that makes an actor wander randomly.
 */
public class WanderBehaviour implements Behaviour {
    /**
     * Returns a move action to a random valid location.
     * @param actor The actor performing the behaviour
     * @param map   The game map
     * @return A move action or null if no valid move exists
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        List<Exit> exits = new ArrayList<>(map.locationOf(actor).getExits());
        Collections.shuffle(exits);
        for (Exit exit : exits) {
            Location destination = exit.getDestination();
            // Important: Must use canActorEnter which checks the ground's rules
            if (!destination.containsAnActor() && destination.canActorEnter(actor)) {
                return new MoveActorAction(destination, exit.getName());
            }
        }
        return null;
    }
}