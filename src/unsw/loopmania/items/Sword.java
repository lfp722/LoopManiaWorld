package unsw.loopmania.items;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {
    public static final int initialPrice = 250;
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public int nextDamage() {
        return (int) this.level.get() + 2;
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
        i.put("type", "Sword");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("level", this.getLevel());
        return i;
    }
    
}
