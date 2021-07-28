package unsw.loopmania.items;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

public class TheOneRing extends Item {
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public void rebirth(LoopManiaWorld world) {
        SimpleIntegerProperty curH = world.getCharacter().getAttr().getCurHealth();
        SimpleIntegerProperty maxH = world.getCharacter().getAttr().getHealth();
        curH.set(maxH.get());
        world.getCharacter().shouldExist().set(true);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject i = new JSONObject();
        i.put("type", "TheOneRing");
        i.put("x", this.getX());
        i.put("y", this.getY());
        return i;
    }
    
}
