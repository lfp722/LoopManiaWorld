package unsw.loopmania;

public class ZombiePit extends Building implements EnemyProducer{

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public void EnemyProducer(LoopManiaWorld world) {
        Zombie zombie = new Zombie(1,1,1,1.0,this.location,1,1,1.0);
        world.addEntity(zombie));
    }
}
