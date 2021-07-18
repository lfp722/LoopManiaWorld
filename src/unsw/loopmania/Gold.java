package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Item;

public class Gold extends Item{
    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y, int value) {
        super(x, y);
        this.ValueInGold.set(value); 
    }

    
}
