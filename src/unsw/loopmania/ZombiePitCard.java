package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitCard extends Card{

    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject card = new JSONObject();
        card.put("x", this.getX());
        card.put("y", this.getY());
        card.put("type", "ZombieCard");
        return card;
    }
    
}
