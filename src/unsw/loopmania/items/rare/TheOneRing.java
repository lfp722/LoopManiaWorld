package unsw.loopmania.items.rare;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Item;

public class TheOneRing extends Item {
    private double chanceAppearance;
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.chanceAppearance = 0.01;
    }
    
    public void setChanceAppearance(double chanceAppearance) {
        this.chanceAppearance = chanceAppearance;
    }
    public double getChanceAppearance() {
        return this.chanceAppearance;
    }
    public void appear(){
        double chanceTrance = Math.random();
        if (chanceTrance <= this.chanceAppearance) {
            return;
        }
        return;
    }
}
