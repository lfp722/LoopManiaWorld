package unsw.loopmania.items.basics;

import unsw.loopmania.items.Item;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
public class Portion extends Item{
    private SimpleDoubleProperty recoverRate;

    public Portion(SimpleIntegerProperty x, SimpleIntegerProperty y,Character owner){
        super(x,y);
        this.recoverRate.set(0.2);
        this.ValueInGold.set(100);
        this.owner = owner;
    }

    public double getRecoverRate() {
        return this.recoverRate.get();
    }

    public void setRecoverRate(double recoverRate) {
        this.recoverRate.set(recoverRate);
    }

    public void recoverHealth() {
        this.owner.curHealth.set(this.owner.curHealth.get() + this.owner.maxHealth.get() * this.recoverRate);
        if (this.owner.curHealth.get() >= this.owner.maxHealth.get()) {
            this.owner.curHealth.get() = this.owner.maxHealth.get();
        }
    }

    @Override
    public void use(){
        if (this.owner != null) {
            this.recoverHealth();
            return;
        }
        throw new RuntimeException("Portion_Error == USE: the owner is not set!");
    }

    @Override
    public void abandon(){
        throw new RuntimeException("Portion_Error == DROP: the Portion cannot be dropped!");
    }
}