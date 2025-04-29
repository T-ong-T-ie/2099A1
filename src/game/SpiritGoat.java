package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A non-hostile creature that wanders in the valley.
 */
public class SpiritGoat extends RottableActor {
    private final WanderBehaviour wanderBehaviour;

    /**
     * Constructor for SpiritGoat.
     */
    public SpiritGoat() {
        super("Spirit Goat", 'y', 50, 10); // 10 turns countdown
        this.addCapability(Status.NON_HOSTILE);
        this.wanderBehaviour = new WanderBehaviour();
    }

    @Override
    public boolean isCurable() {
        return true; // Can be cured by talisman
    }

    @Override
    protected Action getNextAction(ActionList actions, Action lastAction, GameMap map, Display display) {
        Action wander = wanderBehaviour.getAction(this, map);
        if (wander != null) {
            return wander;
        }
        return new DoNothingAction();
    }
}