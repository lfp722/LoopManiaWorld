package unsw.loopmania;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;

public class ElanMuske extends Enemy{
    
    public ElanMuske(PathPosition position, int cycle) {
        super(position, cycle);
        this.setCritRate(0);
        this.setDetectRange(3);
        this.setSupportRange(3);
        this.getAttribute().getAttack().set((int) (Math.pow(this.getLv(), 2.5)*2));
        this.getAttribute().getHealth().set((int) (Math.pow(this.getLv(), 2)*6));
        this.getAttribute().getCurHealth().set(this.getAttribute().getHealth().get());
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath((new Random().nextInt(40) + 10) + 10 * this.getLv());
        this.setExpAfterDeath(20 + 1000 * this.getLv());
        setBoss();
    }

    public void increaseDoggiePrice(LoopManiaWorld world) {
        world.getDoggiePrice().set(world.getDoggiePrice().get() * 3);
    }

    public void decreaseDoggiePrice(LoopManiaWorld world) {
        world.getDoggiePrice().set(world.getDoggiePrice().get() / 4);
    }

    public void healEnemy(List<Enemy> enemies) {
        if ((new Random()).nextInt(100) < 20) {
            for (Enemy e: enemies) {
                if (e.equals(this)) {
                    continue;
                }
                e.getAttribute().getCurHealth().set(e.getAttribute().getHealth().get());
            }
        }
        
    }

    @Override
    public JSONObject toJSON() {
        JSONObject enemy = new JSONObject();
        enemy.put("type", "Elan");
        enemy.put("lv", this.getLv());
        enemy.put("position", this.getPosition().getCurrentPositionInPath());
        return enemy;
    }

}
