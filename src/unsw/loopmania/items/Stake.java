package unsw.loopmania.items;
import unsw.loopmania.*;
import unsw.loopmania.Character;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Stake extends Weapon {

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        //TODO Auto-generated constructor stub
        damage.set(4);
    }


    // @Override
    // public double nextDamage() {
    //     return (double) 0.9 * this.level.get() + 1.5;
    // }

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */

    @Override
    public void specialEffect(Enemy e, Character ch) {
        if (e instanceof Vampire) {
            ch.getStakeVampireBuff().set(3);
        }
        else {
            ch.getStakeVampireBuff().set(1);
        }
    }
}
