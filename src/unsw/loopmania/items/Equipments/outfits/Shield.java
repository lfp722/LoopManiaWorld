package unsw.loopmania.items.Equipments.outfits;

import unsw.loopmania.items.Equipments.Outfit;
import javafx.beans.property.SimpleIntegerProperty;
public class Shield extends Outfit {

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y,isEquipped);
        this.reduceRate = 1 - this.level * 0.15;
    }

    @Override
    public void setReduceRate() {
        this.reduceRate = 1 - this.level * 0.15;
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipShield(this);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().unequipShield(this);
    }
}
