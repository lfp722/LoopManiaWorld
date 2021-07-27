package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.*;

public class Anduril extends Weapon{
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.setValueInGold(5000*this.getLevel());
        //this.damage.set(5);
    }

    @Override
    public void specialEffect(Enemy e, LoopManiaWorld world) {
        if (e.getBoss()) {
            world.getCharacter().getStakeVampireBuff().set(3);
        }
        else {
            world.getCharacter().getStakeVampireBuff().set(1);
        }
    }
}
