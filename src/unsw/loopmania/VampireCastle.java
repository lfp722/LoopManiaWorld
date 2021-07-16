package unsw.loopmania;

import unsw.loopmania.LoopManiaWorld;

public class VampireCastle extends Building implements EnemyProducer{

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy EnemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
        int radius = world.getRadiusCampFire();
        Vampire vampire = new Vampire(pt, radius, world.getCycle().intValue());
        return vampire;
    }

}
