package unsw.loopmania;

import unsw.loopmania.StaticEntity;

public class HeroCastle extends Building{
    private LoopManiaWorld world;
    
    public HeroCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }
    
    public void openStore(LoopManiaWorld world){
        StaticEntity c = new StaticEntity(world.getCharacter().getX(), world.getCharacter().getY());
        StaticEntity castle = new StaticEntity(this.getX(), this.getY());
        if(c.equals(castle)){
            world.openStore();
        }
    }
}
