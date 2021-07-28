package unsw.loopmania.items;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
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
        this.ValueInGold.bind(Bindings.createIntegerBinding(()->currentPrice(),this.level));

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


    public int currentPrice() {
        return 0;
    }
}