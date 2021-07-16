package unsw.loopmania.items;

import java.lang.Math;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;


public class Helmet extends Outfit {
    private SimpleIntegerProperty ownerOriginalDamage;
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.ownerOriginalDamage.set(0);
        //this.reduceRate.set(1 /(Math.sqrt(this.level.get())));
    }

    // @Override
    // public double nextReduceRate() {
    //     return (double) 1 /(Math.sqrt(this.level.get()));
    // }

    // public void setCharacterDamage(boolean equiped) {
    //     if (equiped) {
    //         this.ownerOriginalDamage = this.owner.getAttr().getAttack();
    //         this.owner.setDamage(this.ownerOriginalDamage.get() * (0.85 + 0.01 * this.level.get()));
    //     }
    //     else {
    //         this.owner.setDamage(this.ownerOriginalDamage);
    //         this.ownerOriginalDamage.set(0);
    //     }
    // }

    @Override
    public void equip() {
        owner.getEquip().equipHelmet(this);
        //this.setCharacterDamage(true);
        owner.getDebuff().set(0.8);
        
    }

    @Override
    public void unequip() {
        this.owner.getEquip().dropHelmet();
        //this.setCharacterDamage(false);
    }
    

    //Convenient for later change
    /* 
    @Override
    
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    */
}
