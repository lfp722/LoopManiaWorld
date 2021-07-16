package unsw.loopmania.items;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
import javafx.beans.binding.Bindings;
public class Weapon extends Equipment {

    protected SimpleIntegerProperty damage;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        this.ValueInGold.set(250);
        //this.damage.bind(Bindings.createDoubleBinding(()->this.nextDamage(),this.level));
    }

    public int getDamage() {
        return this.damage.get();
    }

    // public double nextDamage() {
    //     return 0;
    // }

    // @Override
    // public int nextLevelUpPrice() {
    //     return (int) Math.pow((100 * this.level.get()),2) - 150;
    // }

    public void specialEffect(Enemy e, Character ch){
        return;
    }
}
