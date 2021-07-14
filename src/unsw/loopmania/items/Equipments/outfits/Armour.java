package unsw.loopmania.items.Equipments.outfits;

import unsw.loopmania.items.Equipments.Outfit;
import javafx.beans.property.SimpleIntegerProperty;
public class Armour extends Outfit {

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y,isEquipped);
        this.reduceRate = 0.5;
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipArmour(this);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().unequipArmour(this);
    }
}
