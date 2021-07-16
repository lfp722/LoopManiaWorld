package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;
import unsw.loopmania.LoopManiaWorld;

public class Village extends Building{
    private int regenRate;

    public Village(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
        this.regenRate = 20;
    }

    public int getRegenRate(){
        return this.regenRate;
    }

    @Override
    public void specialEffect(LoopManiaWorld world){
        Pair<Integer, Integer> a = new Pair<>(this.getX(), this.getY());
        Pair<Integer, Integer> b = new Pair<>(this.getX(), this.getY());
        if(a.equals(b)){
<<<<<<< HEAD
            world.getCharacter().setHealth(world.getCharacter().heal(regenRate));
=======
            world.getCharacter().heal(regenRate);
>>>>>>> d30da675d54be37139e0a71098e4167f8f8926e3
        }
    }
}