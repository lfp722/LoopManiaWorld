package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

public class Trap extends Building{
    private int damage;

    public Trap(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
        this.damage = 20;
    }

    /**
     * getter
     * @return
     */
    public int getDamage(){
        return this.damage;
    }

    @Override
    public void specialEffect(LoopManiaWorld world){
        Pair<Integer, Integer> a = new Pair<>(this.getX(), this.getY());
        for(Enemy b: world.getEnemies()){
            Pair<Integer, Integer> c = new Pair<>(b.getX(), b.getY());
            if(a.equals(c)){
                b.underAttack(this.getDamage());
                world.removeBuildingEntities(this);
                this.destroy();
            }
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject b = new JSONObject();
        b.put("type", "Trap");
        b.put("x", getX());
        b.put("y", getY());
        return b;
    }
}