package unsw.loopmania;

import java.util.Random;


public abstract class Enemy extends MovingEntity implements Comparable<Enemy>{
    private EntityAttribute attribute;
    private int supportRange;
    private int detectRange;
    private PathPosition position;
    private double critRate;
    private int lv;
    private EnemyCriticalAttack critical;
    private int goldAfterDeath;
    private int expAfterDeath;


    /**
     * 
     * @param position
     * @param cycle
     */
    public Enemy (PathPosition position, int cycle) {
        super(position);
        this.lv = (new Random()).nextInt(cycle);
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
    
    /**
     * 
     * @param detectRange
     */
    public void setDetectRange(int detectRange) {
        this.detectRange = detectRange;
    }

    /**
     * 
     * @return
     */
    public PathPosition getPosition() {
        return position;
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
            applyCriticalAttack(object);
        } else {
            object.underAttack(this.attribute.getAttack().get());
        }

    }
    //add a world as a parametre to satisfy all things to work
    public void attack(Soldier object, LoopManiaWorld world) {
        if ((new Random()).nextInt(100) < this.critRate) {
            applyCriticalAttack(object, world);
        } else {
            object.underAttack(this.attribute.getAttack().get());
        }

    }

    /**
     * decrease health when suffering a damage
     * and check health to determine death condition
     * @param damage damage suffered during an attack
     */
    public void underAttack(int damage) {
        this.attribute.getCurHealth().subtract(damage);
        if (!checkHealth()) {
            destroy();
        }
    }

    /**
     * 
     * @param object
     * @return if an character is in the range of detection
     */
    public boolean detect(Character object) {
        return (Math.pow(object.getX() - this.position.getX().get(), 2) + Math.pow(object.getY() - this.position.getY().get(), 2) < Math.pow(detectRange, 2));
    }

    /**
     * 
     * @param battle
     * @return if a battle is in the range of support
     */
    public boolean ifInSupport(PathPosition battleLocation) {
        return (Math.pow(battleLocation.getX().get() - this.position.getX().get(), 2) + Math.pow(battleLocation.getY().get() - this.position.getY().get(), 2) < Math.pow(supportRange, 2));
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
     * setter method to change behaviour
     * @param eca an critical attack behaviour
     */
    public void setCriticalAttack(EnemyCriticalAttack eca) {
        this.critical = eca;
    }

    /**
     * 
     * @param object
     */
    public void applyCriticalAttack(Character object) {
        critical.criticalAttack(object);
    }

    /**
     * 
     * @param object
     */
    public void applyCriticalAttack(Soldier object, LoopManiaWorld world) {
        critical.criticalAttack(object, world);
    }

    // /**
    //  * TODO: special effect for staff
    //  * @return
    //  */
    // public void applyStaffEffect() {
    //     //destroy the zombie
    //     //create an ally with its attribute
    //     //add it into ally list
    //     this.destory();
    //     Character a = new Character();
        
    // }

    // /**
    //  * TODO: after the special effect
    //  * @return
    //  */
    // public void afterStaffEffect() {
    //     //destroy that ally
    //     //create a zombie with remaining attribute
    //     //add it into enemy list
    // }

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

}
