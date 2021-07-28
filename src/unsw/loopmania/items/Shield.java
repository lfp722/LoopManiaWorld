package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
public class Shield extends Outfit {
    public static final int initialPrice = 250;
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * (this.level.get())),2) + 250;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((100 * (this.level.get() - 1)),2) + 250;
    }

    public void specialEffect(Enemy e, LoopManiaWorld world) {
    }
}
