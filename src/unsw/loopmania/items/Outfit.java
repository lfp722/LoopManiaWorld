package unsw.loopmania.items;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.binding.Bindings;
public class Outfit extends Equipment {

    protected SimpleIntegerProperty defense;

    public Outfit(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        this.ValueInGold.set(250);
        this.defense.set(3);

        //this.defense.bind(Bindings.createIntegerBinding(()->this.nextDefense(),this.level));
        //this.reduceRate.bind(Bindings.createDoubleBinding(()->this.nextReduceRate(),this.level));
    }

    public int getDefense() {
        return this.defense.get();
    }

    // public int nextDefense() {
    //     return (int) this.defense.get() + this.level.get() * 10;
    // }

    // public double getReduceRate() {
    //     return this.reduceRate.get();
    // }

    // @Override
    // public int nextLevelUpPrice() {
    //     return (int) Math.pow((100 * this.level.get()),2) - 150;
    // }
    
    // public double nextReduceRate() {
    //     return 0.5;
    // }
}
