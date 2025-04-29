package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
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
    private Menu menu; // Just declare it, don't initialize here
    private WeaponItem equippedWeapon;

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hit points
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new BareFist());
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(200));
        this.equippedWeapon = null;
    }

    /**
     * Overridden hurt method to handle death messages
     *
     * @param points number of hit points to deduct
     */
    @Override
    public void hurt(int points) {
        super.hurt(points);

        // Check if player is unconscious (dead) after taking damage
        if (!isConscious()) {
            System.out.println(FancyMessage.YOU_DIED);
        }
    }

    /**
     * Apply stamina cost when attacking
     * Called by AttackAction when the player performs an attack
     */
    public void applyAttackStaminaCost() {
        // Standard stamina cost for attacks is 20 points
        int staminaCost = 20;

        // Only apply cost if player has stamina attribute
        if (this.hasAttribute(BaseActorAttributes.STAMINA)) {
            this.modifyAttribute(BaseActorAttributes.STAMINA,
                    ActorAttributeOperations.DECREASE,
                    staminaCost);
        }
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Check if player is unconscious at the beginning of their turn
        if (!isConscious()) {
            // Print message in case the unconscious state was reached by other means
            System.out.println(FancyMessage.YOU_DIED);
            // The engine will handle removal of unconscious actors
            return null;
        }

        // Handle normal turn
        display.println(this + " (" + this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) + "), " +
                "stamina: " + this.getAttribute(BaseActorAttributes.STAMINA));

        // Create a new menu with the current actions
        this.menu = new Menu(actions);

        // Use the public method to show menu
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