package unsw.loopmania;

import unsw.loopmania.LoopManiaWorld;

public class VampireCastle extends Building implements EnemyProducer{

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public Enemy EnemyProducer(LoopManiaWorld world) {
        PathPosition pt = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        int radius = world.getRadiusCampFire();
        Vampire vampire = new Vampire(pt, radius, world.getCycle());
        return vampire;
    }

}
