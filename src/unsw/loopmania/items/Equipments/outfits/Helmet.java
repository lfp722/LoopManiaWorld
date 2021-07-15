package unsw.loopmania.items.Equipments.outfits;

import java.lang.Math;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipments.Outfit;


public class Helmet extends Outfit {
    private SimpleIntegerProperty ownerOriginalDamage;
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x, y, isEquipped);
        this.ownerOriginalDamage.set(0);
        this.reduceRate.set(1 /(Math.sqrt(this.level.get())));
    }

    @Override
    public double nextReduceRate() {
        return (double) 1 /(Math.sqrt(this.level.get()));
    }

    public void setCharacterDamage(boolean equiped) {
        if (equiped) {
            this.ownerOriginalDamage = this.owner.getDamage();
            this.owner.setDamage(this.ownerOriginalDamage.get() * (0.85 + 0.01 * this.level.get()));
        }
        else {
            this.owner.setDamage(this.ownerOriginalDamage);
            this.ownerOriginalDamage.set(0);
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
    

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
