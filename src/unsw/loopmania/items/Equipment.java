package unsw.loopmania.items;
import java.lang.Math;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import javafx.beans.property.SimpleBooleanProperty;
public class Equipment extends Item{

    protected SimpleIntegerProperty level;
    protected SimpleIntegerProperty levelUpPrice;

    public int getLevelUpPrice() {
        return this.levelUpPrice.get();
    }

    public int nextLevelUpPrice() {
        return 0;
    }

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        level = new SimpleIntegerProperty();
        this.level.set(1);
        this.ValueInGold.set(250);

        levelUpPrice = new SimpleIntegerProperty();
        this.levelUpPrice.bind(Bindings.createIntegerBinding(()->nextLevelUpPrice(),this.level));
    }

    public int getLevel() {
        return this.level.get();
    }

    public void levelUp() {
        this.level.set(this.level.get()+1);
    }


    public void equip(Character ch) {

    }
    public void unequip(Character ch) {
    }

    @Override
    public void abandon(){
        throw new IllegalArgumentException("Equipment_Error == ABANDON: the Equipment cannot be abandoned!");
    }
}