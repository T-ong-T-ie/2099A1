package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
    private WeaponItem equippedWeapon;

    /**
     * Constructor.
     *
     * @param name        the name of the Player
     * @param displayChar the character that will represent the Player in the display
     * @param hitPoints   the Player's starting hit points
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new BareFist());

        // Add stamina attribute to player with initial value of 100
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(100));
    }

    /**
     * Decrease the player's health by the specified points
     *
     * @param points the points to decrease health by
     */
    @Override
    public void hurt(int points) {
        boolean wasAlive = isConscious(); // Check if player was alive before taking damage
        super.hurt(points);
    }

    /**
     * Apply stamina cost for attacking
     */
    public void applyAttackStaminaCost() {
        // Decrease stamina by 15 for each attack
        if (this.hasAttribute(BaseActorAttributes.STAMINA)) {
            this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 15);
        }
    }

    /**
     * Returns the actions the player can perform
     *
     * @param actions    collection of possible Actions
     * @param lastAction the Action this Actor took last turn
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return list of actions the player can perform
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Check if player is unconscious at the beginning of their turn
        if (!isConscious()) {
            // Print death message prominently and end the game
            display.println(FancyMessage.YOU_DIED);
            // Force game termination after displaying death message
            map.removeActor(this);  // Remove player from map
            return new DoNothingAction();  // Return DoNothing to prevent further actions
        }

        // Rest of method remains the same
        display.println(this + " (" + this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) + "), " +
                "stamina: " + this.getAttribute(BaseActorAttributes.STAMINA));

        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Method to equip a weapon
     *
     * @param weapon the weapon to equip
     */
    public void equipWeapon(WeaponItem weapon) {
        this.equippedWeapon = weapon;
    }

    /**
     * Method to get the equipped weapon
     *
     * @return the equipped weapon or null if none is equipped
     */
    public WeaponItem getEquippedWeapon() {
        return this.equippedWeapon;
    }
}