package unsw.loopmania.items.Equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.items.Equipments.Weapon;
import javafx.beans.binding.Bindings;
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

        this.chanceTrance.bind(Bindings.createDoubleBinding(()->this.nextChanceTrance(),this.level));
    }
    

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow(200 * this.level.get(), 2) - 200;
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
    @Override
    public double nextDamage() {
        return (double) 0.85 * this.level.get() + 1;
    }
    public double getChanceTrance(){
        return this.chanceTrance.get();
    }
    public double nextChanceTrance(){
        return (double) this.level.get() * 0.15;
    }
    
}
