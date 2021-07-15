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
            this.damage.set(3 * this.level.get() + 5);
        }
        else {
            this.damage.set(0.9 * this.level.get() + 1.5);
        }
    }


    @Override
    public double nextDamage() {
        return (double) 0.9 * this.level.get() + 1.5;
    }

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
