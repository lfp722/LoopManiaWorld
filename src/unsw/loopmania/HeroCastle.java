package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
//import unsw.loopmania.StaticEntity;

public class HeroCastle extends Building{
    
    public HeroCastle(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }
    
    // @Override
    // public void specialEffect(LoopManiaWorld world){
    //     if(this.getX() == world.getCharacter().getX() && this.getY() == world.getCharacter().getY()){
    //         world.openStore();
    //     }
    // }

}