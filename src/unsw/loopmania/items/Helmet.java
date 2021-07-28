package unsw.loopmania.items;

import java.lang.Math;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;


public class Helmet extends Outfit {
    public static final int initialPrice = 250;
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    //Convenient for later change
    
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
        i.put("type", "Helmet");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("level", this.getLevel());
        return i;
    }
    
}
