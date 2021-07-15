package unsw.loopmania.items.Equipments;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.binding.Bindings;
public class Weapon extends Equipment {

    protected SimpleDoubleProperty damage;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y,isEquipped);
        this.ValueInGold.set(250);
        this.damage.set(0);
        this.damage.bind(Bindings.createDoubleBinding(()->this.nextDamage(),this.level));
    }

    public double getDamage() {
        return this.damage.get();
    }

    public double nextDamage() {
        return 0;
    }

    public void hit() {
        return;
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((100 * this.level.get()),2) - 150;
    }

    public void specialEffect(){
        return;
    }
}
