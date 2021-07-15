package unsw.loopmania.items;
import unsw.loopmania.StaticEntity;
import javafx.beans.property.SimpleIntegerProperty;

public class Item extends StaticEntity {
    protected SimpleIntegerProperty ValueInGold;
    protected Character owner;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        this.ValueInGold.set(0);
        this.owner = null;
    }

    public void use() {
        return;
    }

    public void abandon() {
        return;
    }

    public int getValueInGold() {
        return this.ValueInGold.get();
    }

    public void setValueInGold(int ValueInGold) {
        this.ValueInGold.set(ValueInGold);
    }

    public Character getOwner() {
        return this.owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }
}