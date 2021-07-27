package unsw.loopmania;

import java.util.Random;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePit extends Building {

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }


    /**
     * special effect
     * @param world
     * @return
     */
    public Zombie produceZombie(LoopManiaWorld world) {

        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return null;
        }
        if (((new Random()).nextInt(100)) < 20) {
            PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
            Zombie zombie = new Zombie(pt, (new Random()).nextInt(world.getCycle().intValue()));
            world.getEnemies().add(zombie);
            return zombie;
        }
        return null;
        
    }

    @Override
    public JSONObject toJSON() {
        JSONObject b = new JSONObject();
        b.put("type", "ZombiePit");
        b.put("x", getX());
        b.put("y", getY());
        return b;
    }
}
