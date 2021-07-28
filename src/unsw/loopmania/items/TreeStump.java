package unsw.loopmania.items;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;

public class TreeStump extends Shield{
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        //this.reduceRate.set(1 - this.level.get() * 0.15);
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * (this.level.get())),2) + 5000;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((100 * (this.level.get() - 1)),2) + 5000;
    }
    //TODO: check if enemy is boss and increase Defense
    @Override
    public void specialEffect(Enemy e, LoopManiaWorld world) {
        if (e.getBoss()) {
            world.getCharacter().getTsBuff().set(3);
        }
        else {
            world.getCharacter().getTsBuff().set(1);
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "TreeStump");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("level", this.getLevel());
        return i;
    }
}
