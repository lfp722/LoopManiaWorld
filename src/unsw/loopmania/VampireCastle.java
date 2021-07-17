package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class VampireCastle extends Building {

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    public Vampire produceVampire(LoopManiaWorld world) {
        
        if (world.getCycle().get()%5 != 0) {
            return null;
        }
        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return null;
        }
        if (((new Random()).nextInt(100)) < 50) {
            PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
            Vampire vampire = new Vampire(pt, world.getCycle().intValue());
            world.getEnemies().add(vampire);
            return vampire;
        }
        return null;
        
    }

}
