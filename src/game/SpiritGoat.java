package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A non-hostile creature that wanders in the valley.
 */
public class SpiritGoat extends Actor {
    private final WanderBehaviour wanderBehaviour;

    /**
     * Constructor for SpiritGoat.
     */
    public SpiritGoat() {
        super("Spirit Goat", 'y', 50);
        this.addCapability(Status.NON_HOSTILE);
        this.wanderBehaviour = new WanderBehaviour();
    }

    /**
     * Selects a wandering action or does nothing.
     * @param actions    List of possible actions
     * @param lastAction The last action performed
     * @param map        The game map
     * @param display    The display for output
     * @return The selected action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        Action wander = wanderBehaviour.getAction(this, map);
        if (wander != null) {
            return wander;
        }
        return new DoNothingAction();
    }
}