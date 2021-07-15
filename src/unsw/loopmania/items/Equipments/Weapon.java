package unsw.loopmania.items.Equipments;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Weapon extends Equipment {

    protected SimpleDoubleProperty damage;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y,isEquipped);
        this.ValueInGold.set(250);
        this.damage.set(0);
    }

    public double getDamage() {
        return this.damage.get();
    }

    public void setDamage(double damage) {
        this.damage.set(damage);
    }

    public void hit() {
        return;
    }
}
