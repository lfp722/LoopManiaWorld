package unsw.loopmania.items;
import java.lang.Math;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
public class Equipment extends Item implements equipItem{

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
        this.level.set(1);
        this.ValueInGold.set(250);

        this.levelUpPrice.bind(Bindings.createIntegerBinding(()->this.nextLevelUpPrice(),this.level));
    }

    public int getLevel() {
        return this.level.get();
    }

    public void setLevel(int level) {
        this.level.set(this.level.get()+1);
    }


    public void equip() {

    }
    public void unequip() {
    }

    @Override
    public void abandon(){
        throw new IllegalArgumentException("Equipment_Error == ABANDON: the Equipment cannot be abandoned!");
    }
}