package unsw.loopmania.items.Equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.items.Equipments.Weapon;
import java.lang.Math;
/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {
    private double chanceTrance;
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped) {
        super(x, y, isEquipped);
        this.ValueInGold = 400;
        this.chanceTrance = 0.15 * this.level;
        
    }
    
    @Override
    public void setLevelUpPrice() {
        this.levelUpPrice = (int) (Math.pow(200 * this.level, 2) - 200);
    }

    public void trance(BasicEnemy enemy){
        return;
    }


    @Override
    public void hit() {
        super.hit();
        double chanceTrance = Math.random();
        if (chanceTrance <= this.chanceTrance) {
            trance(this.owner.enemy);
        }
         //instance of random class
        
    }

    public void setDamage() {
        this.damage =  0.85 * this.level + 1;
    }
    public double getChanceTrance(){
        return this.chanceTrance;
    }
    public void setChanceTrance(){
        this.chanceTrance = this.level * 0.15;
    }
}
