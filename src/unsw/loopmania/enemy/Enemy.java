package unsw.loopmania.enemy;

import unsw.loopmania.Entity;

public class Enemy extends Entity {
    private int supportRange;
    private int detectRange;
    private Location position;
    private double critRate;
    private int lv;

    /**
     * 
     * @param supportRange
     * @param detectRange
     * @param position
     * @param critRate
     */
    public Enemy(int supportRange, int detectRange, Location position, double critRate, int lv) {
        this.supportRange = supportRange;
        this.detectRange = detectRange;
        this.position = position;
        this.critRate = critRate;
        this.lv = lv;
    }

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
    public Location getPosition() {
        return position;
    }

    /**
     * 
     * @param position
     */
    public void setPosition(Location position) {
        this.position = position;
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
    public int getLv() {
        return lv;
    }

    /**
     * 
     * @param lv
     */
    public void setLv(int lv) {
        this.lv = lv;
    }

    
    
}
