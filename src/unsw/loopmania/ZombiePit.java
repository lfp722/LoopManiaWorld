package unsw.loopmania;

public class ZombiePit extends Building implements EnemyProducer{
    private PathPosition pt;

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy EnemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(world.getOrderedPath().indexOf(this.getNearestPathTile(world)), world.getOrderedPath());
        Zombie zombie = new Zombie(pt, world.getCycle().intValue());
        return zombie;
    }
}
