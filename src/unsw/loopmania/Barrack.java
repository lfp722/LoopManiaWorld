package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Barrack extends Building{

    public Barrack(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
    }

    public void addSoldier(LoopManiaWorld world){
        if(world.getCharacter().getX() == this.getX().get() && world.getCharacter().getY()==(this.getY().get())){
            world.getCharacter().addSoldier();
        }
    }
}
