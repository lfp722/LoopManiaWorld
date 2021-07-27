package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    public JSONObject toJSON() {
        return null;
    }
}
