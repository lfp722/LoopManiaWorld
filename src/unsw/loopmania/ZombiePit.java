package unsw.loopmania;

public class ZombiePit extends Building implements EnemyProducer{
    private PathPosition pt;

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy EnemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(this.getPathTileNearBuilding(world), world.getOrderedPath());
        Zombie zombie = new Zombie(pt, world.getCycle().get());
        return zombie;
    }
}
