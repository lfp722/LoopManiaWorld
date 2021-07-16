package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

public class Trap extends Building{
    private int damage;

    public Trap(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
        this.damage = 20;
    }

    public int getDamage(){
        return this.damage;
    }
<<<<<<< HEAD
    //TODO: pass enemy as para
    public void doDamage(LoopManiaWorld world){
=======

    @Override
    public void specialEffect(LoopManiaWorld world){
>>>>>>> d30da675d54be37139e0a71098e4167f8f8926e3
        Pair<Integer, Integer> a = new Pair<>(this.getX(), this.getY());
        for(Enemy b: world.getEnemies()){
            Pair<Integer, Integer> c = new Pair<>(b.getX(), b.getY());
            if(a.equals(c)){
                b.underAttack(this.getDamage());
                world.removeBuildingEntities(this);
                this.destroy();
            }
        }
    }
}