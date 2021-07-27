package unsw.loopmania.items;

import java.lang.Math;
import javafx.beans.property.SimpleIntegerProperty;


public class Helmet extends Outfit {
    //private SimpleIntegerProperty ownerOriginalDamage;
    public static final int initialPrice = 250;
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    // @Override
    // public void equip(Character ch) {
    //     ch.getEquip().equipHelmet(this);
    //     //this.setCharacterDamage(true);
    //     ch.getDebuff().set(0.8);
        
    // }

    // @Override
    // public void unequip(Character ch) {
    //     ch.getEquip().dropHelmet();
    //     ch.getDebuff().set(1);
    //     //this.setCharacterDamage(false);
    // }
    

    //Convenient for later change
    
    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * (this.level.get())),2) + 250;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((100 * (this.level.get() - 1)),2) + 250;
    }

    public static void main(String[] args) {
        Helmet h = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        System.out.println(h.getValueInGold());
        System.out.println(h.getLevelUpPrice());
        System.out.println(h.getDefense());
        System.out.println(h.getLevel());
    
        h.levelUp();
        System.out.println(h.getValueInGold());
        System.out.println(h.getLevelUpPrice());
        System.out.println(h.getDefense());
        System.out.println(h.getLevel());

    }
    
}
