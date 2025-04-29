package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Base class for actors that can be affected by rot
 */
public abstract class RottableActor extends Actor implements Rottable {
    private int rotTimer;
    private final int initialRotTimer;

    public RottableActor(String name, char displayChar, int hitPoints, int initialRotTimer) {
        super(name, displayChar, hitPoints);
        this.initialRotTimer = initialRotTimer;
        this.rotTimer = initialRotTimer;
    }

    @Override
    public int getRotTimer() {
        return rotTimer;
    }

    @Override
    public void setRotTimer(int time) {
        this.rotTimer = time;
    }

    @Override
    public boolean decrementRotTimer() {
        rotTimer--;
        return rotTimer <= 0;
    }

    @Override
    public void resetRotTimer() {
        rotTimer = initialRotTimer;
    }

    /**
     * The next action this actor will take
     *
     * @param actions    collection of possible Actions
     * @param lastAction The Action this Actor took last turn
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    protected abstract Action getNextAction(ActionList actions, Action lastAction, GameMap map, Display display);

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Check if rot timer has expired
        if (decrementRotTimer()) {
            // Actor turns into a RotWolf when rot timer expires
            map.removeActor(this);
            map.addActor(new RotWolf(map.at(23, 10).getActor()), map.locationOf(this));
            return new DoNothingAction();
        }

        return getNextAction(actions, lastAction, map, display);
    }
}