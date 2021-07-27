package unsw.loopmania.items;
import unsw.loopmania.*;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Stake extends Weapon {
    public static final int initialPrice = 250;
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    @Override
    public int nextDamage() {
        return (int) (0.9 * this.level.get() + 1.5);
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * (this.level.get())),2) + 250;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((100 * (this.level.get() - 1)),2) + 250;
    }

    @Override
    public void specialEffect(Enemy e, LoopManiaWorld world) {
        if (e instanceof Vampire) {
            world.getCharacter().getStakeVampireBuff().set(3);
        }
        else {
            world.getCharacter().getStakeVampireBuff().set(1);
        }
    }
}
