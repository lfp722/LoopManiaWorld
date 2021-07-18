package unsw.loopmania.items;
import java.lang.Math;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import javafx.beans.property.SimpleBooleanProperty;
public class Equipment extends Item{

    protected SimpleIntegerProperty level;
    protected SimpleIntegerProperty levelUpPrice;

    /**
     * 
     * @return int of gold required to upgrade an equipment
     */
    public int getLevelUpPrice() {
        return this.levelUpPrice.get();
    }

    /**
     * to be overriden
     * @return calculate different pattern depending on the level
     */
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

    /**
     * 
     * @return level of an equipment
     */
    public int getLevel() {
        return this.level.get();
    }

    /**
     * upgrade an equipment
     */
    public void levelUp() {
        this.level.set(this.level.get()+1);
    }


    /**
     * equip an equipment
     * @param ch character to be equipped on
     */
    public void equip(Character ch) {

    }

    /**
     * unequip an equipment
     * @param ch
     */
    public void unequip(Character ch) {

    }

    @Override
    public void abandon(){
        throw new IllegalArgumentException("Equipment_Error == ABANDON: the Equipment cannot be abandoned!");
    }
}