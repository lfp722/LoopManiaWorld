package unsw.loopmania.items;
import java.util.Random;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;

public class TheOneRing extends Item {
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isConfusing) {
        super(x, y);
        isRare = true;

        if (isConfusing) {
            int possibility = new Random().nextInt(10);
            if (possibility == Item.DEFENCE) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.treeStump(world, e));
                setSecondValue(EffectFactory.treeStump);
                rareType = Item.DEFENCE;
            }
            else if (possibility == Item.ATTACK) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.anduril(world, e));
                setSecondValue(EffectFactory.anduril);
                rareType = Item.ATTACK;
            }
        } 
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
        i.put("raretype", rareType);
        return i;
    }
    
}
