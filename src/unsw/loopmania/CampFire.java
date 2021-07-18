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
        buffed = false;
    }

    /**
     * 
     * @param e
     * @return if an entity(enemy or character) is in the radius of a campfire buff
     */
    public boolean isInRadius(Entity e) {
        if (Math.sqrt(Math.pow(this.getX() - e.getX(), 2)+Math.pow(this.getY()-e.getY(), 2)) <= radius) {
            return true;
        }
        return false;
    }

    @Override
    public void specialEffect(LoopManiaWorld world){
        if (world.getCharacter().getCampFireBuff().get() == 2) {
            buffed = true;
            return;
        }
        if (isInRadius(world.getCharacter())){
            world.getCharacter().getCampFireBuff().set(2);
            buffed = true;
        }
        else if (buffed) {
            world.getCharacter().getCampFireBuff().set(1);
            buffed = false;
        }
    }

}
