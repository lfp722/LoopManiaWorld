package unsw.loopmania.items.rare;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Item;

public class TheOneRing extends Item {
    private SimpleDoubleProperty chanceAppearance;
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.chanceAppearance.set(0.01);
    }
    
    public void setChanceAppearance(double chanceAppearance) {
        this.chanceAppearance.set(chanceAppearance);
    }
    public double getChanceAppearance() {
        return this.chanceAppearance.get();
    }
    public void appear(){
        double chanceTrance = Math.random();
        if (chanceTrance <= this.chanceAppearance.get()) {
            return;
        }
        return;
    }
}
