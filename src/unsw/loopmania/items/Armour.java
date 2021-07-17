package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
public class Armour extends Outfit {

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        //this.reduceRate.set(0.5);
    }

    @Override
    public void equip(Character ch) {
        ch.getEquip().equipArmour(this);
    }

    @Override
    public void unequip(Character ch) {
        ch.getEquip().dropArmour();
    }

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
