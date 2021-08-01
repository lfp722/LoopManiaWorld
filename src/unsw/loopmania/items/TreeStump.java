package unsw.loopmania.items;

import java.util.Random;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;

public class TreeStump extends Shield{
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isConfusing) {
        super(x,y);
        //this.reduceRate.set(1 - this.level.get() * 0.15);
        isRare = true;

        if (isConfusing) {
            int possibility = new Random().nextInt(10);//possibility = 1;
            if (possibility == Item.ATTACK) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.anduril(world, e));
                setSecondValue(EffectFactory.anduril);
                rareType = Item.ATTACK;
            }
            else if (possibility == Item.HEALTH) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.theOneRing(world));
                rareType = Item.HEALTH;
            }
        } 
    }

    @Override
    public int nextDefense() {
        return 10 + level.get()*10;
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((300 * (this.level.get())),2) + 5000;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((300 * (this.level.get() - 1)),2) + 5000;
    }
    //TODO: check if enemy is boss and increase Defense
    @Override
    public void specialEffect(Enemy e, LoopManiaWorld world) {
        if (e.getBoss()) {
            world.getCharacter().getTsBuff().set(3);
            world.getBattleStatus().add("Attacked by a boss, triple defending buff triggered!\n");

        }
        else {
            world.getCharacter().getTsBuff().set(1);
            world.getBattleStatus().add("Attacked by normal enemy, triple attacking buff cancelled!\n");

        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "TreeStump");
        i.put("x", this.getX());
        i.put("y", this.getY());
        i.put("level", this.getLevel());
        i.put("raretype", rareType);
        return i;
    }
}
