package unsw.loopmania.items;
import java.lang.Math;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
public class Equipment extends Item implements equipItem{
    protected SimpleBooleanProperty isEquipped;
    protected SimpleIntegerProperty level;
    protected SimpleIntegerProperty levelUpPrice;

    public int getLevelUpPrice() {
        return this.levelUpPrice.get();
    }

    public int nextLevelUpPrice() {
        return (int) 0;
    }

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped){
        super(x, y);
        this.isEquipped.set(isEquipped);
        this.level.set(1);
        this.ValueInGold.set(250);

        this.levelUpPrice.bind(Bindings.createIntegerBinding(()->this.nextLevelUpPrice(),this.level));
    }

    public boolean isEquipped() {
        return this.isEquipped.get();
    }

    public void setEquipped(boolean isEquipped) {
        this.isEquipped.set(isEquipped);
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
    public void use(){
        if (this.owner != null) {
            if (!isEquipped.get()) {
                setEquipped(true);
                this.equip();
            }
            else {
                setEquipped(false);
                this.unequip();
            }
            return;
        }
        throw new IllegalArgumentException("Equipment_Error == USE: the owner is not set!");
    }

    @Override
    public void abandon(){
        throw new IllegalArgumentException("Equipment_Error == ABANDON: the Equipment cannot be abandoned!");
    }
}