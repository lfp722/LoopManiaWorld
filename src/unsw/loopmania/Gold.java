package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Item;

public class Gold extends Item{
    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y, int value) {
        super(x, y);
        this.ValueInGold.set(value); 
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "Gold");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("valueInGold", this.getValueInGold());
        return i;
    }
}
