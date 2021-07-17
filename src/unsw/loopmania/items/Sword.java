package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.damage.set(5);
    }

    // @Override
    // public double nextDamage() {
    //     return (double) this.level.get() + 2;
    // }
    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}