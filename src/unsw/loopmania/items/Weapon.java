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
        damage = new SimpleIntegerProperty(0);
        this.ValueInGold.set(250);
        this.damage.bind(Bindings.createIntegerBinding(()->this.nextDamage(),this.level));

        //this.damage.bind(Bindings.createIntegerBinding(()->this.nextDamage(),this.level));
    }

    /**
     * getter
     * @return damage in int
     */
    public int getDamage() {
        return this.damage.get();
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getAttack(){
        return damage;
    }

    /**
     * relationship between damage increase and level
     * @return damage increase in next level in int 
     */
    public int nextDamage() {
        return 0;
    }

    // @Override
    // public int nextLevelUpPrice() {
    //     return (int) Math.pow((100 * this.level.get()),2) - 150;
    // }

    /**
     * possible effect of staff and stake, to be overriden
     * @param e
     * @param world
     */
    public void specialEffect(Enemy e, LoopManiaWorld world){
        return;
    }

    @Override
    public void equip(Character ch) {
        ch.getEquip().equipWeapon(this);
    }

    @Override
    public void unequip(Character ch) {
        ch.getEquip().dropWeapon();
    }
}
