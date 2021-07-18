package unsw.loopmania.items;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TheOneRing extends Item {
    private SimpleDoubleProperty chanceAppearance;
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.chanceAppearance.set(0.01);
    }
    
    /**
     * setter
     * @param chanceAppearance
     */
    public void setChanceAppearance(double chanceAppearance) {
        this.chanceAppearance.set(chanceAppearance);
    }

    /**
     * getter
     * @return
     */
    public double getChanceAppearance() {
        return this.chanceAppearance.get();
    }

    /**
     * show in particular chance
     */
    public void appear(){
        double chanceTrance = Math.random();
        if (chanceTrance <= this.chanceAppearance.get()) {
            return;
        }
        return;
    }
}
