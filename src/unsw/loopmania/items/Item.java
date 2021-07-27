package unsw.loopmania.items;
import unsw.loopmania.*;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;


public class Item extends StaticEntity {
    
    protected SimpleIntegerProperty ValueInGold;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        ValueInGold = new SimpleIntegerProperty();
        this.ValueInGold.set(0);
    }

    /**
     * getter
     * @return the value of item in gold
     * i.e. how much gold will be obtained
     */
    public int getValueInGold() {
        return this.ValueInGold.get();
    }

    /**
     * setter
     * @param ValueInGold
     */
    public void setValueInGold(int ValueInGold) {
        this.ValueInGold.set(ValueInGold);
    }

    public JSONObject toJSON() {
        return null;
    }
}