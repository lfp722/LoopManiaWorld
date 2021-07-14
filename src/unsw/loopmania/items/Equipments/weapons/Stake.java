package unsw.loopmania.items.Equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipments.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Stake extends Weapon {

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped) {
        super(x, y, isEquipped);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public void hit() {
        setDamage();
        super.hit();
    }

    public void setDamage() {
        if (this.owner.enemey.equals("Vampires")) {
            this.damage =  3 * this.level + 5;
        }
        else {
            this.damage =  0.9 * this.level + 1.5;
        }
    }

}
