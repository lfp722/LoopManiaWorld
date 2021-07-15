package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    // TODO = potentially implement relationships between this class and other classes
    private EntityAttribute attr;
    private SimpleIntegerProperty level;
    private SimpleIntegerProperty experience;
    private List<Soldier> army;
    private Bag bag;
    private Equipped equipped;
    private SimpleIntegerProperty gold;
    private SimpleIntegerProperty next_expr;


    public Character(PathPosition position) {
        super(position);
        attr = new EntityAttribute(5, 0, 35);
        level = new SimpleIntegerProperty();
        level.set(1);
        experience = new SimpleIntegerProperty();
        experience.set(0);
        army = new ArrayList<Soldier>();
        bag = new Bag();
        equipped = new Equipped();
        level.bind(Bindings.createDoubleBinding(()->Math.sqrt((double)experience.divide(100).get()), experience));
        next_expr.bind(Bindings.createDoubleBinding(()->Math.pow(level.get()+1,2)*100, level));
    }

    public void addSoldier(Soldier soldier) {
        army.add(soldier);
    }

    public void remSoldier(Soldier soldier) {
        army.remove(soldier);
    }

    public void equip(Item item) {
        item.equip(this);
    }

    public void unequip(Item item) {
        item.unequip(this);
    }

    public void attack(Enemy enemy) {
        int actualAttack = attr.getAttack().get()+equipped.getAttack();
        for (Soldier temp: army) {
            actualAttack += temp.getAttack();
        }
        enemy.underAttack(actualAttack);
        equipped.getWeapon().specialEffect(Enemy enemy);
    }

    public void underAttack(int attack) {
        if (attack > attr.getCurHealth().get() || attack == attr.getCurHealth().get()) {
            this.destroy();
        }
        else {
            attr.getCurHealth().subtract(attack);
        }
    }

    public EntityAttribute getAttr() {
        return attr;
    }

    public Bag getBag() {
        return bag;
    }

    public List<Soldier> getArmy() {
        return army;
    }

    public int getGold() {
        return gold.get();
    }

    public void addGold(int gold) {
        this.gold.set(this.gold.get()+gold);
    }
}
