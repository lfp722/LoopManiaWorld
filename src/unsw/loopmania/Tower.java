package unsw.loopmania;
import java.util.List;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;


public class Tower extends Building{
    private int damage;
    private int radius;


    public Tower(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        this.damage = 10;
        this.radius = 3;
    }

    /**
     * check if an enemy is in radius
     * @param e
     * @return
     */
    public boolean isInRadius(Enemy e) {
        if (Math.sqrt(Math.pow(this.getX() - e.getX(), 2)+Math.pow(this.getY()-e.getY(), 2)) <= radius) {
            return true;
        }
        return false;
    }


    /**
     * special effect
     * @param enemies
     */
    public void attackIfInRadius(List<Enemy> enemies){
        for(Enemy a: enemies){
            if(isInRadius(a)) {
                a.underAttack(damage);
            }
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject b = new JSONObject();
        b.put("type", "Tower");
        b.put("x", getX());
        b.put("y", getY());
        return b;
    }

}