package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.Character;
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
        SimpleIntegerProperty curH = this.owner.getAttr().getCurHealth();
        SimpleIntegerProperty maxH = this.owner.getAttr().getHealth();
        curH.set(curH.get() + (int)(maxH.get() * this.recoverRate.get()));
        if (curH.get() >= maxH.get()) {
            curH.set(maxH.get());
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