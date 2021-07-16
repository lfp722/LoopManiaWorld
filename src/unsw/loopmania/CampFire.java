package unsw.loopmania;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;

import java.util.ArrayList;

import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CampFire extends Building{
    private List<Pair<Integer, Integer>> withinRadius;
    private int radius;


    public CampFire(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        this.radius = 5;
        this.setWithInRange();
    }

    public int getRadius(){
        return this.radius;
    }

    public void setWithInRange(){
        int r = this.getRadius();
        for(int x = this.getX().get() - r;x <= this.getX().get() + r; x++){
            for(int y = this.getY().get() - r;y <= this.getY().get() + r; y++){
                if(x*x + y*y <= r*r){
                    Pair<Integer, Integer> a = new Pair<>(x, y);
                    this.withinRadius.add(a);
                }
            }
        }
    }

    public List<Pair<Integer, Integer>> getWithInRange(){
        return this.withinRadius;
    }

    public void buffCharacter(LoopManiaWorld world){
        if(this.getX() == world.getCharacter().getX() && this.getY() == world.getCharacter().getY()){
            world.getCharacter().setDamage(world.getCharacter().getDamage()*2);
        }
    }

}
