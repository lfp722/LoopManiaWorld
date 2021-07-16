package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class VampireCastle extends Building implements EnemyProducer{

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy enemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
        Vampire vampire = new Vampire(pt, world.getCycle().intValue());
        return vampire;
    }

}
