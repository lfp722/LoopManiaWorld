package unsw.loopmania.items.Equipments;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.binding.Bindings;
public class Outfit extends Equipment {

    protected SimpleIntegerProperty defense;
    protected SimpleDoubleProperty reduceRate;

    public Outfit(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y, isEquipped);
        this.ValueInGold.set(250);
        this.defense.set(100);
        this.reduceRate.set(0);

        this.defense.bind(Bindings.createIntegerBinding(()->this.nextDefense(),this.level));
        this.reduceRate.bind(Bindings.createDoubleBinding(()->this.nextReduceRate(),this.level));
    }

    public int getDefense() {
        return this.defense.get();
    }

    public int nextDefense() {
        return (int) this.defense.get() + this.level.get() * 10;
    }

    public double getReduceRate() {
        return this.reduceRate.get();
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }
    
    public double nextReduceRate() {
        return 0.5;
    }
}
