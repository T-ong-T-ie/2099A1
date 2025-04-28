package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
    private Item equippedWeapon;

    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new BareFist());
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(200));
        this.equippedWeapon = null;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Display HP, stamina, and equipped weapon
        String weaponDisplay = (equippedWeapon != null) ? equippedWeapon.toString() : "BareFist";
        display.println(this + ", Stamina=" + getAttribute(BaseActorAttributes.STAMINA) + "/" +
                getAttributeMaximum(BaseActorAttributes.STAMINA) + ", Weapon=" + weaponDisplay);

        if (lastAction != null && lastAction.getNextAction() != null) {
            return lastAction.getNextAction();
        }

        // Check for Talisman on the ground
        Location currentLocation = map.locationOf(this);
        for (Item item : currentLocation.getItems()) {
            if (item instanceof Talisman) {
                actions.add(new PickUpAction(item));
            }
        }

        // Add attack actions for nearby non-player actors
        for (Exit exit : currentLocation.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && !destination.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
                actions.add(new AttackAction(destination.getActor()));
            }
        }

        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    @Override
    public void addItemToInventory(Item item) {
        super.addItemToInventory(item);
        if (item instanceof Talisman) {
            equippedWeapon = item;
        }
    }

    public Weapon getWeapon() {
        if (equippedWeapon != null && equippedWeapon instanceof Talisman && ((Talisman) equippedWeapon).canAttack(this)) {
            return (Talisman) equippedWeapon;
        }
        return getIntrinsicWeapon();
    }

    public void applyAttackStaminaCost() {
        if (equippedWeapon != null && equippedWeapon instanceof Talisman && ((Talisman) equippedWeapon).canAttack(this)) {
            ((Talisman) equippedWeapon).applyStaminaCost(this);
        }
    }
}