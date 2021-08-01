package unsw.loopmania;

import java.util.Random;

import org.json.JSONObject;

public class Zombie extends Enemy {
    private int critRateLimit = 60;
    private int cycle;

    public Zombie(PathPosition position, int cycle) {
        super(position, cycle);
        if (this.getLv() * 10 > critRateLimit) {
            this.setCritRate(critRateLimit);
        } else {
            this.setCritRate(this.getLv() * 10);
        }
        this.setDetectRange(3);
        this.setSupportRange(5);
        this.getAttribute().getAttack().set((int) (this.getLv() * 10 + 1));
        this.getAttribute().getHealth().set((int) (this.getLv() * 10));
        this.getAttribute().getCurHealth().set(this.getAttribute().getHealth().get());
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath(new Random().nextInt(100)+100+15*this.getLv());
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
        object.underAttack(this, (int) (this.getAttribute().getAttack().get() * 1.5));
    }

    @Override
    public void criticalAttack(Enemy object) {
        object.underAttack((int) (this.getAttribute().getAttack().get() * 1.2));
    }

    @Override
    public void criticalAttack(Soldier object, LoopManiaWorld world) {
        world.getBattleStatus().add("Soldier " + world.getCharacter().getArmy().indexOf(object) + "has been tranced into a Zombie!\n");
        EntityAttribute attr = object.dead();
        Zombie a = new Zombie(this.getPosition(), cycle, attr);
        world.getEnemies().add(a);
    }

    @Override
    public void move(LoopManiaWorld world) {
        
        int directionChoice = (new Random()).nextInt(4);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
        
    }

    @Override
    public JSONObject toJSON() {
        JSONObject enemy = new JSONObject();
        enemy.put("type", "Zombie");
        enemy.put("lv", this.getLv());
        enemy.put("position", this.getPosition().getCurrentPositionInPath());
        return enemy;
    }

}
