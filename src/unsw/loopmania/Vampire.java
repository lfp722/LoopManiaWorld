package unsw.loopmania;

import java.util.Random;

public class Vampire extends Enemy implements EnemyCriticalAttack {
    
    private int radusCampfire;
    private int critRateLimit = 90;
    private boolean movingDirection;

    /**
     * 
     * @param supportRange
     * @param detectRange
     * @param position
     * @param critRate
     * @param radusCampfire
     */
    public Vampire(PathPosition position, int radusCampfire, int cycle) {
        super(position, cycle);
        this.radusCampfire = radusCampfire;
        if (this.getLv() * 10 > critRateLimit) {
            this.setCritRate(critRateLimit);
        } else {
            this.setCritRate(this.getLv() * 10);
        }
        this.setDetectRange(3);
        this.setSupportRange(4);
        this.getAttribute().getAttack().set((int) Math.pow(this.getLv(), 2));
        this.getAttribute().getHealth().set((int) (Math.pow(this.getLv(), 1.5) * 3));
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

    //get the coordinate of the position on step before and after, 
    //calculate the distance between them and the campfire, 
    //decide the moving direction after that.
    //hint: orderedPath.get(currentPositionInPath).getValue0/1()
    //TODO: confusing in passing world in
    public boolean spotCampfire(LoopManiaWorld world) {
        for (Building c: world.getBuildingEntities()) {
            if (!(c instanceof CampFire)) {
                continue;
            }
            if ((Math.pow(c.getX().get() - this.getPosition().getX().get(), 2)
                     + Math.pow(c.getY().get() - this.getPosition().getX().get(), 2))
                      > Math.pow(this.getSupportRange(), 2)) {
                continue;
            }
            int tileFrontX = this.getPosition().getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() + 1) % this.getPosition().getOrderedPath().size())
                .getValue0();
            int tileFrontY = this.getPosition().getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() + 1) % this.getPosition().getOrderedPath().size())
                .getValue1();
            int tileAfterX = this.getPosition().getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() - 1 + this.getPosition().getOrderedPath().size()) % this.getPosition().getOrderedPath().size())
                .getValue0();
            int tileAfterY = this.getPosition().getOrderedPath()
                .get((this.getPosition().getCurrentPositionInPath() - 1 + this.getPosition().getOrderedPath().size()) % this.getPosition().getOrderedPath().size())
                .getValue1();
            int tileCurrentX = this.getPosition().getX().get();
            int tileCurrentY = this.getPosition().getY().get();
            int fireX = c.getX().get();
            int fireY = c.getY().get();
            double distance2Front = Math.pow(tileFrontX - fireX, 2) + Math.pow(tileFrontY - fireY, 2);
            double distance2After = Math.pow(tileAfterX - fireX, 2) + Math.pow(tileAfterY - fireY, 2);
            double distance2Current = Math.pow(tileCurrentX - fireX, 2) + Math.pow(tileCurrentY - fireY, 2);
            if (distance2Front > distance2Current && distance2Current > distance2After) {
                return true;
            } else if (distance2After > distance2Current && distance2Current > distance2Front) {
                return false;
            } else {
                continue;
            }
            
        }
        return true;
    }

    /**
     * move away from campfire if found
     */
    @Override
    public void move(LoopManiaWorld world) {
        if (!spotCampfire(world)) {
            this.movingDirection = !this.movingDirection;
        } 
        if (this.movingDirection) {
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
}
