package unsw.loopmania.items.Equipments.outfits;

import java.lang.Math;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipments.Outfit;
public class Helmet extends Outfit {
    private int ownerOriginalDamage;
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x, y, isEquipped);
        this.ownerOriginalDamage = 0;
        this.reduceRate = 1 /(Math.sqrt(this.level));
    }

    @Override
    public void setReduceRate() {
        this.reduceRate = 1 /(Math.sqrt(this.level));
    }

    public void setCharacterDamage(boolean equiped) {
        if (equiped) {
            this.ownerOriginalDamage = this.owner.getDamage();
            this.owner.setDamage(this.ownerOriginalDamage * (0.85 + 0.01 * this.level));
        }
        else {
            this.owner.setDamage(this.ownerOriginalDamage);
            this.ownerOriginalDamage = 0;
        }
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipHelmet(this);
        this.setCharacterDamage(true);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().unequipHelmet(this);
        this.setCharacterDamage(false);
    }
}
