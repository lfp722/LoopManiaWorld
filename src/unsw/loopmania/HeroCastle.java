package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public class HeroCastle extends Building{
    private LoopManiaWorld world;
    
    public HeroCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }
    
    public void openStore(LoopManiaWorld world){
        if(this.getX() == world.getCharacter().getX() && this.getY() == world.getCharacter().getY()){
            world.openStore();
        }
    }
}