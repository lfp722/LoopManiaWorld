package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerCard extends Card{

    public TowerCard (SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject card = new JSONObject();
        card.put("x", this.getX());
        card.put("y", this.getY());
        card.put("type", "TowerCard");
        return null;
    }
    
}
