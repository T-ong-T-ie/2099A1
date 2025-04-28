package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class RotWolf extends Actor {
    private final WanderBehaviour wanderBehaviour;
    private final FollowBehaviour followBehaviour;
    private Actor target;

    public RotWolf(Actor target) {
        super("Rot Wolf", 'r', 60);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new WolfFang()); // Use the concrete implementation
        this.wanderBehaviour = new WanderBehaviour();
        this.followBehaviour = new FollowBehaviour(target, 5);
        this.target = target;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Attack the Farmer if adjacent
        for (Exit exit : map.locationOf(this).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && destination.getActor() == target) {
                return new AttackAction(target);
            }
        }

        // Follow the Farmer if within 5 steps, otherwise wander
        Action follow = followBehaviour.getAction(this, map);
        if (follow != null) {
            return follow;
        }

        Action wander = wanderBehaviour.getAction(this, map);
        if (wander != null) {
            return wander;
        }
        return new DoNothingAction();
    }
}