package unsw.loopmania.items;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.*;

public class Anduril extends Weapon{

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isConfusing) {
        super(x, y);
        //this.damage.set(5);
        isRare = true;
        if (isConfusing) {
            int possibility = 0;
            if (possibility == Item.DEFENCE) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.treeStump(world, e));
                setSecondValue(EffectFactory.treeStump);
                rareType = Item.DEFENCE;
            }
            else if (possibility == Item.HEALTH) {
                setSe((LoopManiaWorld world, Enemy e)->EffectFactory.theOneRing(world));
                rareType = Item.HEALTH;
            }
        }    
    }

    @Override
    public int nextDamage() {
        return 5*this.level.get()+10;
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * (this.level.get())),2) + 5000;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((100 * (this.level.get() - 1)),2) + 5000;
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
