package unsw.loopmania.items;

import java.lang.Math;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;


public class Helmet extends Outfit {
    //private SimpleIntegerProperty ownerOriginalDamage;
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        // ownerOriginalDamage
        // this.ownerOriginalDamage.set(0);
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
    public void equip(Character ch) {
        ch.getEquip().equipHelmet(this);
        //this.setCharacterDamage(true);
        ch.getDebuff().set(0.8);
        
    }

    @Override
    public void unequip(Character ch) {
        ch.getEquip().dropHelmet();
        ch.getDebuff().set(1);
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
