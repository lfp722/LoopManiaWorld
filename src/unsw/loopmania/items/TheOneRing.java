package unsw.loopmania.items;
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
    
}
