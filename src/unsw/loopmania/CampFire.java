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
    private boolean buffed;

    public CampFire(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        this.radius = 5;
        this.setWithInRange();
        buffed = false;
    }

    public int getRadius(){
        return this.radius;
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

    public List<Pair<Integer, Integer>> getWithInRange(){
        return this.withinRadius;
    }

    @Override
    public void specialEffect(LoopManiaWorld world){
        if (world.getCharacter().getCampFireBuff().get() == 2) {
            buffed = true;
            return;
        }
        if (this.getX() == world.getCharacter().getX() && this.getY() == world.getCharacter().getY()){
            world.getCharacter().getCampFireBuff().set(2);
            buffed = true;
        }
        else if (buffed) {
            world.getCharacter().getCampFireBuff().set(1);
            buffed = false;
        }
    }

}
