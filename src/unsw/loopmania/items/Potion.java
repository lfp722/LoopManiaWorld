package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

import org.json.JSONObject;

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

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "Potion");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("valueInGold", this.getValueInGold());
        return i;
    }

}