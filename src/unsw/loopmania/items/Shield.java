package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
public class Shield extends Outfit {
    public static final int initialPrice = 250;
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        //this.reduceRate.set(1 - this.level.get() * 0.15);
    }

    // @Override
    // public double nextReduceRate() {
    //     return (double) 1 - this.level.get() * 0.15;
    // }

    @Override
    public void equip(Character ch) {
        ch.getEquip().equipShield(this);
    }

    @Override
    public void unequip(Character ch) {
        if (ch.getEquip().getShield().equals(this)) {
            ch.getEquip().dropShield();
        }
    }

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
