package unsw.loopmania.items.Equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipments.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped) {
        super(x, y, isEquipped);
        this.damage = this.level + 2;
    }
    
    @Override
    public void hit() {
        super.hit();
    }

    public void setDamage() {
        this.damage = this.level + 2;
    }
}
