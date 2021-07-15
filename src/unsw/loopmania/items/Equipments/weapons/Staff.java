package unsw.loopmania.items.Equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.items.Equipments.Weapon;
import java.lang.Math;
/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {
    private SimpleDoubleProperty chanceTrance;
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped) {
        super(x, y, isEquipped);
        this.ValueInGold.set(400);
        this.chanceTrance.set(0.15 * this.level.get());
        
    }
    
    @Override
    public void setLevelUpPrice() {
        this.levelUpPrice.set((int) (Math.pow(200 * this.level.get(), 2) - 200));
    }

    public void trance(BasicEnemy enemy){
        return;
    }


    @Override
    public void hit() {
        super.hit();
        double chanceTrance = Math.random();
        if (chanceTrance <= this.chanceTrance.get()) {
            trance(this.owner.enemy);
        }
         //instance of random class
        
    }

    public void setDamage() {
        this.damage.set(0.85 * this.level.get() + 1);
    }
    public double getChanceTrance(){
        return this.chanceTrance.get();
    }
    public void setChanceTrance(){
        this.chanceTrance.set(this.level.get() * 0.15);
    }

}
