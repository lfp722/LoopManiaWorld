package unsw.loopmania;

import java.util.Random;

import org.json.JSONObject;

public class Slug extends Enemy {

    public Slug(PathPosition position, int cycle) {
        super(position, cycle);
        this.setCritRate(0);
        this.setDetectRange(3);
        this.setSupportRange(3);
        this.getAttribute().getAttack().set((int) (Math.pow(this.getLv(), 2/3)*2));
        this.getAttribute().getHealth().set((int) (this.getLv()*3));
        this.getAttribute().getCurHealth().set(this.getAttribute().getHealth().get());
        this.getAttribute().getDefence().set(0);
        this.setGoldAfterDeath((new Random().nextInt(40) + 10) + 10 * this.getLv());
        this.setExpAfterDeath(20 + 100 * this.getLv());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject enemy = new JSONObject();
        enemy.put("type", "Slug");
        enemy.put("lv", this.getLv());
        enemy.put("position", this.getPosition().getCurrentPositionInPath());
        return enemy;
    }
    
}
