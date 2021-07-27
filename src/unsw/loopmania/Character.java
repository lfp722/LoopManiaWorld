package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    private Equipped equipped;
    private SimpleIntegerProperty gold;
    private SimpleIntegerProperty next_expr;
    private SimpleIntegerProperty campFireBuff;
    private SimpleIntegerProperty stakeVampireBuff;
    private SimpleDoubleProperty debuff;
    private LoopManiaWorld world;
    private boolean isStunned;


    public Character(PathPosition position, LoopManiaWorld world) {
        super(position);
        this.world = world;
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
        campFireBuff.set(1);
        stakeVampireBuff.set(1);
        debuff.set(1);
        level.bind(Bindings.createDoubleBinding(()->Math.sqrt((double)experience.divide(3000).get())+1, experience));
        next_expr.bind(Bindings.createDoubleBinding(()->Math.pow(level.get()+1,2)*3000, level));
        attr.getHealth().bind(level.multiply(20).add(15));
        attr.getDefence().set(0);
        attr.getAttack().bind(Bindings.createDoubleBinding(()->(double)level.multiply(2).add(3).get()*campFireBuff.get()*stakeVampireBuff.get()*debuff.get(), level,campFireBuff, stakeVampireBuff, debuff));
        this.equipped = world.getEquip();
        tranced = new ArrayList<>();
        isStunned = false;
    }

    /**
     * add a soldier into army
     * @param soldier
     */
    public void addSoldier(Soldier soldier) {
        army.add(soldier);
    }

    /**
     * remove a soldier from army
     * @param soldier
     */
    public void remSoldier(Soldier soldier) {
        army.remove(soldier);
    }


    /**
     * attack specific enemy
     * @param enemy
     */
    public void attack(Enemy enemy) {
        int actualAttack = attr.getAttack().get()+equipped.getAttack();
        equipped.specialAttack(enemy, world);
        enemy.underAttack(actualAttack);
    }

    /**
     * getter for stunned status
     * @return
     */
    public boolean isStunned() {
        return isStunned;
    }

    public void setStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    /**
     * to be called when it is attacked by an enemy
     * @param attack
     */
    public void underAttack(Enemy e, int attack) {
        equipped.specialDefence(e, world);
        if (attack <= equipped.getDefence().get()) {
            return;
        }
        else{
            attack = attack - equipped.getDefence().get();
        }
        if (attack > attr.getCurHealth().get() || attack == attr.getCurHealth().get()) {
            attr.getCurHealth().set(0);
            this.destroy();
        }
        else {
            attr.getCurHealth().set(attr.getCurHealth().get() - attack);
        }
    }

    /**
     * get attribute of a character
     * @return EntityAttribute containing all attrs
     */
    public EntityAttribute getAttr() {
        return attr;
    }


    /**
     * getter
     * @return
     */
    public List<Soldier> getArmy() {
        return army;
    }

    /**
     * getter
     * @return
     */
    public int getGold() {
        return gold.get();
    }

    /**
     * obtain gold
     * @param gold
     */
    public void addGold(int gold) {
        this.gold.set(this.gold.get()+gold);
    }

    /**
     * getter
     * @return
     */
    public int getExp() {
        return experience.get();
    }

    /**
     * obtain exp
     * @param exp
     */
    public void addExp(int exp) {
        this.experience.set(this.experience.get()+exp);
    }

    /**
     * getter
     * @return
     */
    public Equipped getEquip() {
        return equipped;
    }

    /**
     * to increase the character's current health
     * @param health
     */
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

    /**
     * receive buff
     * @return
     */
    public SimpleIntegerProperty getCampFireBuff() {
        return campFireBuff;
    }

    /**
     * receive buff
     * @return
     */
    public SimpleIntegerProperty getStakeVampireBuff() {
        return stakeVampireBuff;
    }

    /**
     * receive debuff(e.g. helmet)
     * @return
     */
    public SimpleDoubleProperty getDebuff() {
        return debuff;
    }

    /**
     * list of enemy being tranced during a battle
     * @return List<Enemy>
     */
    public List<Enemy> getTranced() {
        return tranced;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getLevel() {
        return level;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getExperience() {
        return experience;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getG() {
        return gold;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getNextLvExp() {
        return next_expr;
    }


    public SimpleIntegerProperty getTsBuff() {
        return equipped.getTsBuff();
    }

    public JSONObject characterToJson() {
        JSONObject character = new JSONObject();
        character.put("curHealth", this.getAttr().getCurHealth().get());
        character.put("exp", this.getExp());
        character.put("gold", this.getGold());
        JSONArray army = new JSONArray();
        for (Soldier s: this.army) {
            army.put(s.toJSON());
        }
        character.put("army", army);
        // JSONArray tranced = new JSONArray();
        // for (Enemy e: this.tranced) {
        //     tranced.put(e.toJSON());
        // }
        // character.put("tranced", tranced);
        return character;
    }


}
