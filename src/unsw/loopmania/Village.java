package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
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

    @Override
    public JSONObject toJSON() {
        JSONObject b = new JSONObject();
        b.put("type", "Village");
        b.put("x", getX());
        b.put("y", getY());
        return b;
    }
}