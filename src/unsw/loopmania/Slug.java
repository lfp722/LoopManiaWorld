package unsw.loopmania;

import java.util.Random;

public class Slug extends Enemy {

    public Slug(PathPosition position, int cycle) {
        super(position, cycle);
        this.setCritRate(0);
        this.setDetectRange(10);
        this.setSupportRange(11);
        this.getAttribute().getAttack().set((int) (Math.pow(this.getLv(), 2/3)*2));
        this.getAttribute().getHealth().set((int) (this.getLv()*3));
        this.getAttribute().getCurHealth().set(this.getAttribute().getHealth().get());
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath((new Random().nextInt(40) + 10) + 10 * this.getLv());
        this.setExpAfterDeath(20 + 100 * this.getLv());
    }
    
}
