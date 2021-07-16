package unsw.loopmania;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

public class Tower extends Building{
    private List<Pair<Integer, Integer>> withinRadius;
    private int damage;
    private int radius;


    public Tower(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        this.damage = 20;
        this.radius = 5;
        this.setWithInRange();
    }

    public void setWithInRange(){
        int r = this.getRadius();
        for(int x = this.getX() - r;x <= this.getX() + r; x++){
            for(int y = this.getY() - r;y <= this.getY() + r; y++){
                if(x*x + y*y <= r*r){
                    Pair<Integer, Integer> a = new Pair<>(x, y);
                    this.withinRadius.add(a);
                }
            }
        }
    }

    public int getRadius(){
        return this.radius;
    }

    public int getDamage(){
        return this.damage;
    }

    public List<Pair<Integer, Integer>> getWithInRadius(){
        return this.withinRadius;
    }

    public void attackIfInRadius(LoopManiaWorld world){
        for(Enemy a: world.getEnemies()){
            Pair<Integer, Integer> b = new Pair<>(a.getX(), a.getY());
            if(this.getWithInRadius().contains(b)){
                a.underAttack(this.getDamage());
            }
        }
    }

}