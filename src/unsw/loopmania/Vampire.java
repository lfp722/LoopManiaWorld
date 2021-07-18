package unsw.loopmania;

import java.util.Random;

public class Vampire extends Enemy {
    
    private int radusCampfire;
    private int critRateLimit = 90;

    /**
     * 
     * @param supportRange
     * @param detectRange
     * @param position
     * @param critRate
     * @param radusCampfire
     */
    public Vampire(PathPosition position, int cycle) {
        super(position, cycle);
        this.radusCampfire = 2;
        if (this.getLv() * 10 > critRateLimit) {
            this.setCritRate(critRateLimit);
        } else {
            this.setCritRate(this.getLv() * 10);
        }
        this.setDetectRange(10);
        this.setSupportRange(11);
        this.getAttribute().getAttack().set((int) Math.pow(this.getLv(), 2));
        this.getAttribute().getHealth().set((int) (Math.pow(this.getLv(), 1.5) * 3 + 50));
        this.getAttribute().getCurHealth().set(this.getAttribute().getHealth().get());
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath((new Random().nextInt(1300) + 200));
        this.setExpAfterDeath(500 + 500 * this.getLv());
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

    /**
     * check if a campfire is in support range
     * @param world
     * @return
     */
    public boolean spotCampfire(LoopManiaWorld world) {
        for (Building c: world.getBuildingEntities()) {
            if (!(c instanceof CampFire)) {
                continue;
            }
            if ((Math.pow(c.getX() - this.getX(), 2)
                     + Math.pow(c.getY() - this.getX(), 2))
                      > Math.pow(this.getSupportRange(), 2)) {
                continue;
            }
            int tileFrontX = world.getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() + 1) % world.getOrderedPath().size())
                .getValue0();
            int tileFrontY = world.getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() + 1) % world.getOrderedPath().size())
                .getValue1();
            int tileAfterX = world.getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() - 1 + world.getOrderedPath().size()) % world.getOrderedPath().size())
                .getValue0();
            int tileAfterY = world.getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() - 1 + world.getOrderedPath().size()) % world.getOrderedPath().size())
                .getValue1();
            int tileCurrentX = this.getPosition().getX().get();
            int tileCurrentY = this.getPosition().getY().get();
            int fireX = c.getX();
            int fireY = c.getY();
            double distance2Front = Math.pow(tileFrontX - fireX, 2) + Math.pow(tileFrontY - fireY, 2);
            double distance2After = Math.pow(tileAfterX - fireX, 2) + Math.pow(tileAfterY - fireY, 2);
            double distance2Current = Math.pow(tileCurrentX - fireX, 2) + Math.pow(tileCurrentY - fireY, 2);
            if (distance2Front > distance2Current ) {
                return true;
            } else if (distance2After > distance2Current ) {
                return false;
            } else {
                continue;
            }
            
        }
        return false;
    }

    /**
     * move away from campfire if found
     */
    @Override
    public void move(LoopManiaWorld world) {
        
        if (!spotCampfire(world)) {
            moveUpPath();
        } else {
            moveDownPath();
        }
    }

    @Override
    public void criticalAttack(Character object) {
        object.underAttack(this.getAttribute().getAttack().get() * (new Random().nextInt(30) + 10) / 100);
    }
    
    @Override
    public void criticalAttack(Soldier object, LoopManiaWorld world) {
        object.underAttack(this.getAttribute().getAttack().get() * (new Random().nextInt(30) + 10) / 100);
    }

    @Override
    public void criticalAttack(Enemy object) {
        object.underAttack(this.getAttribute().getAttack().get() * (new Random().nextInt(30) + 10) / 100);
    }
}
