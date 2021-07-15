package unsw.loopmania.items.Equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipments.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped) {
        super(x, y, isEquipped);
        this.damage.set(this.level.get() + 2);
    }
    
    @Override
    public void hit() {
        super.hit();
    }
    @Override
    public double nextDamage() {
        return (double) this.level.get() + 2;
    }
    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
