package unsw.loopmania.items;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
public class Armour extends Outfit {
    public static final int initialPrice = 250;
    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
    }


    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * (this.level.get())),2) + 250;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((100 * (this.level.get() - 1)),2) + 250;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "Armour");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("valueInGold", this.getValueInGold());
        return i;
    }
    
}
