package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipment;
import unsw.loopmania.items.Item;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity{
    // TODO = potentially implement relationships between this class and other classes
    private EntityAttribute attr;
    private SimpleIntegerProperty level;
    private SimpleIntegerProperty experience;
    private List<Soldier> army;
    private List<Enemy> tranced;
    private Bag bag;
    private Equipped equipped;
    private SimpleIntegerProperty gold;
    private SimpleIntegerProperty next_expr;
    private SimpleIntegerProperty campFireBuff;
    private SimpleIntegerProperty stakeVampireBuff;
    private SimpleDoubleProperty debuff;


    public Character(PathPosition position, Equipped equipments) {
        super(position);
        attr = new EntityAttribute(5, 0, 35);
        level = new SimpleIntegerProperty();
        level.set(1);
        experience = new SimpleIntegerProperty();
        experience.set(0);
        gold = new SimpleIntegerProperty();
        next_expr = new SimpleIntegerProperty();
        campFireBuff = new SimpleIntegerProperty();
        stakeVampireBuff = new SimpleIntegerProperty();
        debuff = new SimpleDoubleProperty();
        army = new ArrayList<>();
        bag = new Bag();
        campFireBuff.set(1);
        stakeVampireBuff.set(1);
        debuff.set(1);
        level.bind(Bindings.createDoubleBinding(()->Math.sqrt((double)experience.divide(100).get())+1, experience));
        next_expr.bind(Bindings.createDoubleBinding(()->Math.pow(level.get()+1,2)*100, level));
        attr.getHealth().bind(level.multiply(20).add(15));
        attr.getDefence().set(0);
        attr.getAttack().bind(Bindings.createDoubleBinding(()->(double)level.multiply(2).add(3).get()*campFireBuff.get()*stakeVampireBuff.get()*debuff.get(), level,campFireBuff, stakeVampireBuff, debuff));
        this.equipped = equipments;
    }

    public void addSoldier(Soldier soldier) {
        army.add(soldier);
    }

    public void remSoldier(Soldier soldier) {
        army.remove(soldier);
    }

    public void equip(Equipment item) {
        item.equip();
    }

    public void unequip(Equipment item) {
        item.unequip();
    }

    public void attack(Enemy enemy) {
        int actualAttack = attr.getAttack().get()+equipped.getAttack();
        equipped.specialAttack(enemy, this);
        enemy.underAttack(actualAttack);
    }

    public void underAttack(int attack) {
        if (attack <= equipped.getDefence()) {
            return;
        }
        else{
            attack = attack - equipped.getDefence();
        }
        if (attack > attr.getCurHealth().get() || attack == attr.getCurHealth().get()) {
            attr.getCurHealth().set(0);
            this.destroy();
        }
        else {
            attr.getCurHealth().set(attr.getCurHealth().get() - attack);
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

    public int getExp() {
        return experience.get();
    }

    public void addExp(int exp) {
        this.experience.set(this.experience.get()+exp);
    }

    public Equipped getEquip() {
        return equipped;
    }

    public void heal(int health) {
        SimpleIntegerProperty curH = attr.getCurHealth();
        SimpleIntegerProperty maxH = attr.getHealth();
        if (curH.get()+health <= maxH.get()) {
            curH.set(curH.get()+health);
        }
        else {
            curH.set(maxH.get());
        }
    }

    public SimpleIntegerProperty getCampFireBuff() {
        return campFireBuff;
    }

    public SimpleIntegerProperty getStakeVampireBuff() {
        return stakeVampireBuff;
    }

    public SimpleDoubleProperty getDebuff() {
        return debuff;
    }

    public List<Enemy> getTranced() {
        return tranced;
    }

    public SimpleIntegerProperty getLevel() {
        return level;
    }

    public SimpleIntegerProperty getExperience() {
        return experience;
    }

    public SimpleIntegerProperty getG() {
        return gold;
    }

}
