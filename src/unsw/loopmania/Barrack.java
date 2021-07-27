package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class Barrack extends Building{

    public Barrack(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
    }

    /**
     * special effect of a barrack, create a soldier and add it into army
     * @param world
     * @return Soldier created, or null when character not passing by
     */
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

    @Override
    public JSONObject toJSON() {
        JSONObject b = new JSONObject();
        b.put("type", "Barrack");
        b.put("x", getX());
        b.put("y", getY());
        return b;
    }
}