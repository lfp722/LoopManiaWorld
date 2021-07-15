package unsw.loopmania;
import java.util.List;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;

public class Trap extends Building{
    private LoopManiaWorld world;

    public Trap(SimpleIntegerProperty x, SimpleIntegerProperty y, int damage){
        super(x,y);
        this.damage = damage;
    }

    public int getDamage(){
        return this.damage;
    }

    public void doDamage(LoopManiaWorld world){
        StaticEntity trap = new StaticEntity(this.getX(), this.getY());
        for(BasicEnemy b: world.getEnemies()){
            StaticEntity monster = new StaticEntity(b.getX(), b.getY());
            if(trap.equals(monster)){
                b.underAttack();
                world.removeBuildingEntities(this);
            }
        }
    }
}
