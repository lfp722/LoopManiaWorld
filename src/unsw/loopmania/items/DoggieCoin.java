package unsw.loopmania.items;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class DoggieCoin extends Item{
    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y, SimpleIntegerProperty price){
        super(x,y);
        this.ValueInGold.bind(price);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "DoggieCoin");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("valueInGold", this.getValueInGold());
        return i;
    }
}
