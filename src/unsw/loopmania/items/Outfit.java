package unsw.loopmania.items;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.binding.Bindings;
public class Outfit extends Equipment {

    protected SimpleIntegerProperty defense;

    public Outfit(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        defense = new SimpleIntegerProperty();
        this.defense.set(3);

        this.defense.bind(Bindings.createIntegerBinding(()->this.nextDefense(),this.level));
    }

    /**
     * getter
     * @return defense increases by the outfit
     */
    public int getDefense() {
        return this.defense.get();
    }

    /**
     * relation between the defense increase next level and the level
     * @return
     */
    public int nextDefense() {
        return (int) 3 + this.level.get() * 10;
    }


    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }

    public SimpleIntegerProperty getDefenceProperty() {
        return defense;
    }
}
