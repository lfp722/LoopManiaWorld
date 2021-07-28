package unsw.loopmania;

public class Doggie extends Enemy{
    private int critRateLimit = 40;

    public Doggie(PathPosition position, int cycle) {

    
        super(position, cycle);
        if (this.getLv() * 10 > critRateLimit) {
            this.setCritRate(critRateLimit);
        } else {
            this.setCritRate(this.getLv() * 10);
        }
        this.setDetectRange(3);
        this.setSupportRange(3);
        this.getAttribute().getAttack().set((int) (Math.pow(this.getLv(), 2)*2));
        this.getAttribute().getHealth().set((int) (Math.pow(this.getLv(), 2)*5));
        this.getAttribute().getCurHealth().set(this.getAttribute().getHealth().get());
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath(0);
        this.setExpAfterDeath(500 + 800 * this.getLv());
        setBoss();
    }

    @Override
    public void criticalAttack(Character object) {
        attack(object);
        object.setStunned(true);
    }

    @Override
    public void criticalAttack(Enemy object) {
        attack(object);
    }

    @Override
    public void criticalAttack(Soldier object, LoopManiaWorld world) {
        attack(object, world);
    }

}
