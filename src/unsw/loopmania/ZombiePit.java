package unsw.loopmania;

public class ZombiePit extends Building implements EnemyProducer{
    private PathPosition pt;

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy EnemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Zombie zombie = new Zombie(pt, world.getCycle());
        return zombie;
    }
}
