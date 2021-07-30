package unsw.loopmania;

import java.util.Random;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class VampireCastle extends Building {

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    /**
     * special effect
     * @param world
     * @return
     */
    public Vampire produceVampire(LoopManiaWorld world) {
        if (((new Random()).nextInt(100)) < 1) {
            return produce(world);
        }
        return null; 
    }

    public Vampire produce(LoopManiaWorld world) {
        if (world.getCycle().get()%5 != 0) {
            return null;
        }
        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return null;
        }
        PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
        Vampire vampire = new Vampire(pt, (new Random()).nextInt(world.getCycle().intValue()));
        world.getEnemies().add(vampire);
        return vampire;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject b = new JSONObject();
        b.put("type", "VampireCastle");
        b.put("x", getX());
        b.put("y", getY());
        return b;
    }

}
