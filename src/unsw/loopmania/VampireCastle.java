package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class VampireCastle extends Building {

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public void specialEffect(LoopManiaWorld world) {
        
        if (world.getCycle().get()%5 != 4) {
            return;
        }
        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return;
        }
        if (((new Random()).nextInt(100)) < 50) {
            PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
            Vampire vampire = new Vampire(pt, world.getCycle().intValue());
            world.getEnemies().add(vampire);
        }
        
    }

}
