package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Barrack extends Building{

    public Barrack(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
    }

    public void addSoldier(LoopManiaWorld world){
        if(world.getCharacter().getX() == this.getX() && world.getCharacter().getY() == this.getY()){
            Soldier soldier = new Soldier(this.simpleX(), this.simpleY(), world.getCharacter());
            world.getCharacter().addSoldier(soldier);
        }
    }
}