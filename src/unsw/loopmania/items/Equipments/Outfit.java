package unsw.loopmania.items.Equipments;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
public class Outfit extends Equipment {

    protected SimpleIntegerProperty defense;
    protected SimpleDoubleProperty reduceRate;

    public Outfit(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y, isEquipped);
        this.ValueInGold.set(250);
        this.defense.set(100);
        this.reduceRate.set(0);
    }

    public int getDefense() {
        return this.defense.get();
    }

    public void setDefense() {
        this.defense.set(this.defense.get() + this.level.get() * 10);
    }

    public double getReduceRate() {
        return this.reduceRate.get();
    }

    public void setReduceRate() {
        this.reduceRate.set(0.5);
    }

}
