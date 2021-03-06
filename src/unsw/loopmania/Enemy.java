package unsw.loopmania;

import java.util.Random;

import org.json.JSONObject;


public abstract class Enemy extends MovingEntity implements Comparable<Enemy> {
    private EntityAttribute attribute;
    private int supportRange;
    private int detectRange;
    private double critRate;
    private int lv;
    private int goldAfterDeath;
    private int expAfterDeath;
    private boolean isBoss = false;


    /**
     * 
     * @param position
     * @param cycle
     */
    public Enemy (PathPosition position, int level) {
        super(position);
        this.attribute = new EntityAttribute();
        this.lv = level + 1;
    }

    /**
     * 
     * getters and setters
     */
    /**
     * 
     * @return
     */
    public int getSupportRange() {
        return supportRange;
    }

    /**
     * 
     * @param supportRange
     */
    public void setSupportRange(int supportRange) {
        this.supportRange = supportRange;
    }

    /**
     * 
     * @return
     */
    public int getDetectRange() {
        return detectRange;
    }
    
    public void setDetectRange(int d) {
        this.detectRange = d;
    }
    
    /**
     * 
     * @return
     */
    public double getCritRate() {
        return critRate;
    }

    /**
     * 
     * @param critRate
     */
    public void setCritRate(double critRate) {
        this.critRate = critRate;
    }

    /**
     * 
     * @return
     */
    public EntityAttribute getAttribute() {
        return attribute;
    }

    

    /**
     * 
     * @param attribute
     */
    public void setAttribute(EntityAttribute attribute) {
        this.attribute = attribute;
    }

    /**
     * 
     * @return
     */
    public int getLv() {
        return lv;
    }

    /**
     * 
     * @return
     */
    public int getGoldAfterDeath() {
        return goldAfterDeath;
    }

    /**
     * 
     * @param goldAfterDeath
     */
    public void setGoldAfterDeath(int goldAfterDeath) {
        this.goldAfterDeath = goldAfterDeath;
    }

    /**
     * 
     * @return
     */
    public int getExpAfterDeath() {
        return expAfterDeath;
    }

    /**
     * 
     * @param expAfterDeath
     */
    public void setExpAfterDeath(int expAfterDeath) {
        this.expAfterDeath = expAfterDeath;
    }

    /**
     * 
     * actions of enemy
    */
    /**
     * 
     * @param object
     */
    public void attack(Character object) {
        if ((new Random()).nextInt(100) < this.critRate) {
            criticalAttack(object);
        } else {
            object.underAttack(this, this.attribute.getAttack().get());
            
        }

    }


    public void attack(Enemy object) {
        if ((new Random()).nextInt(100) < this.critRate) {
            criticalAttack(object);
        } else {
            object.underAttack(this.attribute.getAttack().get());
        }

    }


    //add a world as a parametre to satisfy all things to work
    public void attack(Soldier object, LoopManiaWorld world) {
        if ((new Random()).nextInt(100) < this.critRate) {
            criticalAttack(object, world);
        } else {
            object.underAttack(this.attribute.getAttack().get());
            String name = "Soldier" + world.getCharacter().getArmy().indexOf(object);
            String eName = this.toJSON().getString("type") + world.getEnemies().indexOf(this);
            world.getBattleStatus().add(eName + " attacks " + name);
            world.getBattleStatus().add(name + " suffers " + this.getAttribute().getAttack().get() + "points of damage\n");

        }

    }

    /**
     * take in different type of object of its attack, have different behaviour
     * @param 
     */

    public void criticalAttack(Character object){

    }

    public void criticalAttack(Soldier object, LoopManiaWorld world){

    }

    public void criticalAttack(Enemy object){

    }

    /**
     * decrease health when suffering a damage
     * and check health to determine death condition
     * @param damage damage suffered during an attack
     */
    public void underAttack(int damage) {
        if (damage >= attribute.getCurHealth().get()) {
            attribute.getCurHealth().set(0);
            destroy();
            return;
        }
        this.attribute.getCurHealth().set(this.attribute.getCurHealth().get()-damage);
    }

    /** 
     * 
     * movement pattern of an enemy
    */

    public void move(LoopManiaWorld world) {
        
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
        
    }

    /**
     * Helper methods
     */
    /**
     * 
     * @return if health of an enemy is greater than 0
     */
    public boolean checkHealth() {
        return (this.getAttribute().getCurHealth().get() > 0);
    }
    
    /**
     * a compare helper function to help sort the enemy list
     * @param a
     * @param b
     * @return
     */
    @Override
    public int compareTo(Enemy b) {
        
        Integer valA = this.getAttribute().getCurHealth().get();
        Integer valB = b.getAttribute().getCurHealth().get();
        return valA.compareTo(valB);
    }

    public boolean getBoss() {
        return isBoss;
    }

    public void setBoss() {
        isBoss = true;
    }

    public JSONObject toJSON() {
        return null;
    }

}
