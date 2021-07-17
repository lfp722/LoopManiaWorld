package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
public class Shield extends Outfit {

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        //this.reduceRate.set(1 - this.level.get() * 0.15);
    }

    // @Override
    // public double nextReduceRate() {
    //     return (double) 1 - this.level.get() * 0.15;
    // }

    @Override
    public void equip() {
        this.owner.getEquip().equipShield(this);
    }

    @Override
    public void unequip() {
        if (this.owner.getEquip().getShield().equals(this)) {
            this.owner.getEquip().dropShield();
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