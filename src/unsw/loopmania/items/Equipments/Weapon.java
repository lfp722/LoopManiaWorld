package unsw.loopmania.items.Equipments;
import javafx.beans.property.SimpleIntegerProperty;
public class Weapon extends Equipment {

    protected double damage;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y,boolean isEquipped) {
        super(x,y,isEquipped);
        this.ValueInGold = 250;
        this.damage = 0;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void hit() {
        return;
    }
}
