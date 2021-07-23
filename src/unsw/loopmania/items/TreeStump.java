package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class TreeStump extends Outfit{
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        //this.reduceRate.set(1 - this.level.get() * 0.15);
    }
}
