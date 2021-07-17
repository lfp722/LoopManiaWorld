package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Barrack extends Building{

    public Barrack(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
    }

    public Soldier soldierProducer(LoopManiaWorld world){
        if(world.getCharacter().getX() == this.getX() && world.getCharacter().getY() == this.getY()){
            if (world.getCharacter().getArmy().size() == LoopManiaWorld.soldierWidth) {
                world.removeSoldier(0);
            }
            Soldier soldier = new Soldier(new SimpleIntegerProperty(world.getCharacter().getArmy().size()), new SimpleIntegerProperty(LoopManiaWorld.soldierHeight), world.getCharacter());
            world.getCharacter().addSoldier(soldier);
            return soldier;
        } else {
            return null;
        }
    }
}