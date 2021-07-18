package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;
//import unsw.loopmania.LoopManiaWorld;

public class Village extends Building{
    private int regenRate;

    public Village(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
        this.regenRate = 20;
    }

    /**
     * getter
     * @return
     */
    public int getRegenRate(){
        return this.regenRate;
    }

    @Override
    public void specialEffect(LoopManiaWorld world){
        if(world.getCharacter().getX() == this.getX() && world.getCharacter().getY() == this.getY()){
            world.getCharacter().heal(regenRate);
        }
    }
}