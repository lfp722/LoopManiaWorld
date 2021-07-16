package unsw.loopmania;

import java.util.Random;

public class Zombie extends Enemy {
    private int critRateLimit = 90;
    private int cycle;

    public Zombie(PathPosition position, int cycle) {
        super(position, cycle);
        if (this.getLv() * 10 > critRateLimit) {
            this.setCritRate(critRateLimit);
        } else {
            this.setCritRate(this.getLv() * 10);
        }
        this.setDetectRange(2);
        this.setSupportRange(3);
        this.getAttribute().getAttack().set((int) (this.getLv() * 5 + 1));
        this.getAttribute().getHealth().set((int) (this.getLv() * 5));
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath((new Random().nextInt(100) + 100));
        this.setExpAfterDeath(200 + 150 * this.getLv());
        this.cycle = cycle;
    }

    /**
     * 
     * @param position
     * @param cycle
     * @param attr
     */
    public Zombie(PathPosition position, int cycle, EntityAttribute attr) {
        super(position, cycle);
        if (this.getLv() * 10 > 90) {
            this.setCritRate(90);
        } else {
            this.setCritRate(this.getLv() * 10);
        }
        this.setDetectRange(2);
        this.setSupportRange(3);
        this.setAttribute(attr);
        this.setGoldAfterDeath((new Random().nextInt(100) + 100));
        this.setExpAfterDeath(200 + 150 * this.getLv());
        this.cycle = cycle;
    }

    @Override
    public void criticalAttack(Character object) {
        object.underAttack(this.getAttribute().getAttack().get() * (new Random().nextInt(10) + 5) / 100);
    }

    @Override
    public void criticalAttack(Enemy object) {
        object.underAttack(this.getAttribute().getAttack().get() * (new Random().nextInt(10) + 5) / 100);
    }

    @Override
    public void criticalAttack(Soldier object, LoopManiaWorld world) {
        EntityAttribute attr = object.dead();
        Zombie a = new Zombie(this.getPosition(), cycle, attr);
        world.getEnemies().add(a);
    }

}
