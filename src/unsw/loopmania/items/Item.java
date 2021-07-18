package unsw.loopmania.items;
import unsw.loopmania.*;
import unsw.loopmania.Character;
import javafx.beans.property.SimpleIntegerProperty;


public class Item extends StaticEntity {
    
    protected SimpleIntegerProperty ValueInGold;
    protected Character owner;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        ValueInGold = new SimpleIntegerProperty();
        this.ValueInGold.set(0);
        this.owner = null;
    }

    /**
     * use an item(potion)
     */
    public void use() {
        return;
    }

    /**
     * destroy an item
     */
    public void abandon() {
        return;
    }

    /**
     * getter
     * @return the value of item in gold
     * i.e. how much gold will be obtained
     */
    public int getValueInGold() {
        return this.ValueInGold.get();
    }

    /**
     * setter
     * @param ValueInGold
     */
    public void setValueInGold(int ValueInGold) {
        this.ValueInGold.set(ValueInGold);
    }

    /**
     * getter
     * @return the owner
     */
    public Character getOwner() {
        return this.owner;
    }

    /**
     * setter
     * @param owner
     */
    public void setOwner(Character owner) {
        this.owner = owner;
    }
}