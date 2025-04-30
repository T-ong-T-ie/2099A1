package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;


public class FollowBehaviour implements Behaviour {
    private final Actor target;
    private final int maxDistance;

    public FollowBehaviour(Actor target, int maxDistance) {
        this.target = target;
        this.maxDistance = maxDistance;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);
        Location targetLocation = map.locationOf(target);

        // Check distance to target
        int distance = Math.abs(actorLocation.x() - targetLocation.x()) + Math.abs(actorLocation.y() - targetLocation.y());
        if (distance > maxDistance) {
            return null;
        }

        // Find the closest exit to the target
        Exit closestExit = null;
        int minDistance = Integer.MAX_VALUE;
        for (Exit exit : actorLocation.getExits()) {
            Location destination = exit.getDestination();
            // Check both canActorEnter and that the ground itself allows entry
            if (!destination.containsAnActor() && destination.canActorEnter(actor)) {
                int newDistance = Math.abs(destination.x() - targetLocation.x()) + Math.abs(destination.y() - targetLocation.y());
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    closestExit = exit;
                }
            }
        }

        if (closestExit != null) {
            return new MoveActorAction(closestExit.getDestination(), closestExit.getName());
        }
        return null;
    }
}