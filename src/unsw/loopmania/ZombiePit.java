package unsw.loopmania;

import java.util.Random;

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
            Zombie zombie = new Zombie(pt, world.getCycle().intValue());
            world.getEnemies().add(zombie);
            return zombie;
        }
        return null;
        
    }
}
