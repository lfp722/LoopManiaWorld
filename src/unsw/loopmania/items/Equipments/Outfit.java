package unsw.loopmania.items.Equipments;
import javafx.beans.property.SimpleIntegerProperty;
public class Outfit extends Equipment {

    protected int defense;
    protected double reduceRate;

    public Outfit(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y, isEquipped);
        this.ValueInGold = 250;
        this.defense = 100;
        this.reduceRate = 0;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense += this.level * 10;
    }

    public double getReduceRate() {
        return this.reduceRate;
    }

    public void setReduceRate() {
        this.reduceRate = 0.5;
    }
}
