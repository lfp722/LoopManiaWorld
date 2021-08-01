package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
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

    private SimpleIntegerProperty techPoints;
    private SimpleIntegerProperty attackPoints;
    private SimpleIntegerProperty defencePoints;
    private SimpleIntegerProperty healthPoints;

    private SimpleIntegerProperty rage;
    private SimpleBooleanProperty miss;
    private boolean healAbility;

    private boolean counterMiss = false;


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
        next_expr.bind(Bindings.createDoubleBinding(()->(Math.pow(level.get(),2)+1)*3000, level));

        techPoints = new SimpleIntegerProperty();
        attackPoints = new SimpleIntegerProperty(1);
        healthPoints = new SimpleIntegerProperty(1);
        defencePoints = new SimpleIntegerProperty(1);
        rage = new SimpleIntegerProperty(1);
        miss = new SimpleBooleanProperty(false);

        techPoints.bind(Bindings.createIntegerBinding(()->level.get()+2-attackPoints.get()-healthPoints.get()-defencePoints.get(), level,attackPoints,defencePoints,healthPoints));

        attr.getHealth().bind(healthPoints.multiply(20).add(15));
        attr.getDefence().bind(defencePoints);
        attr.getAttack().bind(Bindings.createDoubleBinding(()->(double)attackPoints.multiply(2).add(3).get()*campFireBuff.get()*stakeVampireBuff.get()*debuff.get()*rage.get(), attackPoints,campFireBuff, stakeVampireBuff, debuff, rage));
        this.equipped = world.getEquip();
        tranced = new ArrayList<>();
        isStunned = false;

        healAbility = false;
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
        if (isStunned) {
            isStunned = false;
            return;
        }
        equipped.specialAttack(enemy, world);
        if (tranced.contains(enemy)) {
            return;
        }
        int actualAttack = attr.getAttack().get()+equipped.getAttack();
        enemy.underAttack(actualAttack);
        String name = enemy.toJSON().getString("type") + world.getEnemies().indexOf(enemy);
        world.getBattleStatus().add("You" + " attacked " + name + "\n");
        world.getBattleStatus().add(name + " suffered " + actualAttack + " points of damage\n");

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
        world.getBattleStatus().add("You have been attacked by " + e.toJSON().getString("type") + world.getEnemies().indexOf(e) + "!\n");
        if (counterMiss) {
            setMiss();
            if (miss.get()) {
                world.getBattleStatus().add("Missed!\n");
                return;
            }
        }
        equipped.specialDefence(e, world);
        String curH = getAttr().getCurHealth().get() + "/" + getAttr().getHealth().get();
        if (attack <= equipped.getDefence().get()) {
            world.getBattleStatus().add("Your outfit is tough enough! No damage suffered! Your health is: " + curH + "\n");
            return;
        }
        else{
            attack = attack - equipped.getDefence().get();
        }
        if (attack > attr.getCurHealth().get() || attack == attr.getCurHealth().get()) {
            attr.getCurHealth().set(0);
            this.destroy();
            world.getBattleStatus().add("Too much damage suffered, you have been killed!\n");
        }
        else {
            attr.getCurHealth().set(attr.getCurHealth().get() - attack);
            curH = getAttr().getCurHealth().get() + "/" + getAttr().getHealth().get();
            world.getBattleStatus().add("You have suffered " + attack + " points of damage. Your health is: " + curH + "\n");
        }
    }

    /**
     * get attribute of a character
     * @return EntityAttribute containing all attrs
     */
    public EntityAttribute getAttr() {
        return attr;
    }

    
    public SimpleIntegerProperty getTechPoints() {
        return techPoints;
    }

    /**
     * getter
     * @return
     */
    public List<Soldier> getArmy() {
        return army;
    }

    

    public void setArmy(List<Soldier> army) {
        this.army = army;
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

    

    public SimpleIntegerProperty getAttackPoints() {
        return attackPoints;
    }


    public SimpleIntegerProperty getDefencePoints() {
        return defencePoints;
    }


    public SimpleIntegerProperty getHealthPoints() {
        return healthPoints;
    }

    

    public SimpleIntegerProperty getRage() {
        return rage;
    }

    public SimpleBooleanProperty getMiss() {
        return miss;
    }

    public boolean isCounterMiss() {
        return counterMiss;
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

    public void initialize() {
        this.getPosition().setCurrentPositionInPath(0);
        experience.set(0);
        System.out.println(this.shouldExist().get());
        gold.set(0);

        for  (Soldier s: army) {
            s.destroy();
        }
        army.clear();

        campFireBuff.set(1);
        stakeVampireBuff.set(1);
        debuff.set(1);
        shouldExist().set(true);

        attackPoints.set(1);
        healthPoints.set(1);
        defencePoints.set(1);
        attr.getCurHealth().set(attr.getHealth().get());

        isStunned = false;

        rage.unbind();
        counterMiss = false;
        healAbility = false;
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
        character.put("position", this.getPosition().getCurrentPositionInPath());
        character.put("attpoints", attackPoints.get());
        character.put("defpoints", defencePoints.get());
        character.put("healthpoints", healthPoints.get());
        return character;
    }

    public void setRage() {
        rage.bind(Bindings.createIntegerBinding(()->angry(), this.getAttr().getCurHealth(), this.getAttr().getHealth()));
    }

    private int angry() {
        int curH = this.getAttr().getCurHealth().get();
        int maxH = this.getAttr().getHealth().get();
        double ratio = (double)curH/maxH;
        if (ratio < 0.3) {
            return 2;
        }
        return 1;
    }

    public void setMiss() {
        int possibility = new Random().nextInt(100);
        if (possibility < 20) {
            miss.set(true);
        }
        else {
            miss.set(false);
        }
    }

    public void startMiss() {
        counterMiss = true;
    }

    public void addAttackPoints() {
        if (techPoints.get()<=0) {
            return;
        }
        attackPoints.set(attackPoints.get()+1);
        if (attackPoints.get() == 10) {
            setRage();
        }
    }

    public void addHealthPoints() {
        if (techPoints.get()<=0) {
            return;
        }
        healthPoints.set(healthPoints.get()+1);
        if (healthPoints.get() == 10) {
            setHeal();
        }
    }

    public void addDefencePoints() {
        if (techPoints.get()<=0) {
            return;
        }
        defencePoints.set(defencePoints.get()+1);
        if (defencePoints.get() == 10) {
            startMiss();
        }
    }

    public void setHeal() {
        healAbility = true;
    }

    public boolean isHeal() {
        return healAbility;
    }
}
