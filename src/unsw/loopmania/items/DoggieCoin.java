package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class DoggieCoin extends Item{
    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y, SimpleIntegerProperty price){
        super(x,y);
        this.ValueInGold.bind(price);
    }
}
