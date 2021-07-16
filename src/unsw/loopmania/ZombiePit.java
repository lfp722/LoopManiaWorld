package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePit extends Building implements EnemyProducer{

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy enemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
        Zombie zombie = new Zombie(pt, world.getCycle().intValue());
        return zombie;
    }
}
