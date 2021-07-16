package unsw.loopmania;

import unsw.loopmania.LoopManiaWorld;

public class Village extends Building{
    private LoopManiaWorld world;

    public Village(SimpleIntegerProperty x, SimpleIntegerProperty y, int regenRate){
        super(x,y);
        this.regenRate = regenRate;
    }

    public int getRegenRate(){
        return this.getRegenRate();
    }

    public void healCharacter(LoopManiaWorld world){
        StaticEntity c = new StaticEntity(world.getCharacter().getX(), world.getCharacter().getY());
        StaticEntity village = new StaticEntity(this.getX(), this.getY());
        if(village.equals(c)){
            world.getCharacter().setHealth(world.getCharacter().getHealth()+this.getRegenRate());
        }
    }
}
