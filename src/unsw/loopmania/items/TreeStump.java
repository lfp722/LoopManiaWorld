package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;

public class TreeStump extends Shield{
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        this.setValueInGold(5000*this.getLevel());
        //this.reduceRate.set(1 - this.level.get() * 0.15);
    }

    //TODO: check if enemy is boss and increase Defense
    @Override
    public void specialEffect(Enemy e, LoopManiaWorld world) {
        if (e.getBoss()) {
            world.getCharacter().getTsBuff().set(3);
        }
        else {
            world.getCharacter().getTsBuff().set(1);
        }
    }
}
