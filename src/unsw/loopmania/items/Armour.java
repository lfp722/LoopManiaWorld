package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
public class Armour extends Outfit {

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        //this.reduceRate.set(0.5);
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipArmour(this);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().dropArmour();
    }

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
