package unsw.loopmania;

import unsw.loopmania.LoopManiaWorld;

public class VampireCastle extends Building implements EnemyProducer{

    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public void EnemyProducer(LoopManiaWorld world) {
        //loop = num of cycle
        if(loop!=0 && loop%5 == 0){
            Vampire vampire = new Vampire(1,1,1,1.0,this.location,1,1,1.0);
            world.addEntity(vampire);
        }
    }

}
