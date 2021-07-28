package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import java.lang.Math;
import java.util.Random;

import org.json.JSONObject;

import unsw.loopmania.*;
/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {
    private SimpleIntegerProperty chanceTrance;
    public static final int initialPrice = 400;
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.chanceTrance = new SimpleIntegerProperty(20);
    }

    @Override
    public int nextDamage() {
        return (int) 0.85 * this.level.get() + 1;
    }
    
    @Override
    public void specialEffect(Enemy enemy, LoopManiaWorld world) {
        if (new Random().nextInt(100) > this.chanceTrance.get()) {
            return;
        }
        world.getCharacter().getTranced().add(enemy);
        world.getEnemies().remove(enemy);
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((200 * (this.level.get())),2) + 400;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((200 * (this.level.get() - 1)),2) + 400;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "Staff");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("level", this.getLevel());
        return i;
    }
    
}
