package unsw.loopmania.items;

import java.util.Random;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.*;

public class Anduril extends Weapon{

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isConfusing) {
        super(x, y);
        //this.damage.set(5);
        isRare = true;
        if (isConfusing) {
            int possibility = (new Random()).nextInt(10);
            if (possibility == Item.DEFENCE) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.treeStump(world, e));
                setSecondValue(EffectFactory.treeStump);
                rareType = Item.DEFENCE;
            }
            else if (possibility == Item.HEALTH) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.theOneRing(world));
                setRareType(Item.HEALTH);
            }
        }    
    }

    @Override
    public int nextDamage() {
        return 5*this.level.get()+10;
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((300 * (this.level.get())),2) + 5000;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((300 * (this.level.get() - 1)),2) + 5000;
    }

    @Override
    public void specialEffect(Enemy e, LoopManiaWorld world) {
        if (e.getBoss()) {
            world.getCharacter().getStakeVampireBuff().set(3);
        }
        else {
            world.getCharacter().getStakeVampireBuff().set(1);
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "Anduril");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("level", this.getLevel());
        i.put("raretype", rareType);
        return i;
    }
}
