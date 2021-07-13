package unsw.loopmania.enemy;

public class Vampire extends Enemy {
    
    private int radusCampfire;

    /**
     * 
     * @param supportRange
     * @param detectRange
     * @param position
     * @param critRate
     * @param radusCampfire
     */
    public Vampire(int supportRange, int detectRange, Location position, double critRate, int radusCampfire, int lv) {
        super(supportRange, detectRange, position, critRate, lv);
        this.radusCampfire = radusCampfire;
    }

    /**
     * 
     * @return
     */
    public int getRadusCampfire() {
        return radusCampfire;
    }

    /**
     * 
     * 
     * @param radusCampfire
     */
    public void setRadusCampfire(int radusCampfire) {
        this.radusCampfire = radusCampfire;
    }

    
}
