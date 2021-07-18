package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;


import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.Character;
public class Potion extends Item{
    private SimpleDoubleProperty recoverRate;
    public static final int initialPrice = 1000;

    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
        recoverRate = new SimpleDoubleProperty();
        this.recoverRate.set(0.2);
        this.ValueInGold.set(1000);
    }

    // public double getRecoverRate() {
    //     return this.recoverRate.get();
    // }

    // public void setRecoverRate(double recoverRate) {
    //     this.recoverRate.set(recoverRate);
    // }

    /**
     * heal specific character
     * @param ch
     */
    public void recoverHealth(Character ch) {
        SimpleIntegerProperty curH = ch.getAttr().getCurHealth();
        SimpleIntegerProperty maxH = ch.getAttr().getHealth();
        curH.set(curH.get() + (int)(maxH.get() * this.recoverRate.get()));
        if (curH.get() >= maxH.get()) {
            curH.set(maxH.get());
        }
    }

    // @Override
    // public void use(){
    //     if (this.owner != null) {
    //         this.recoverHealth();
    //         return;
    //     }
    //     throw new RuntimeException("Portion_Error == USE: the owner is not set!");
    // }

    // @Override
    // public void abandon(){
    //     throw new RuntimeException("Portion_Error == DROP: the Portion cannot be dropped!");
    // }
}