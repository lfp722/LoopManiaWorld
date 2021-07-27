package unsw.loopmania.goal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import unsw.loopmania.*;

public class FinalGoal implements Goal{
    private List<Goal> goals;

    private GoldGoal goldGoal;
    private ExpGoal expGoal;
    private CycleGoal cycleGoal;

    public FinalGoal() {
        goals = new ArrayList<>();
        goldGoal = new GoldGoal(0);
        expGoal = new ExpGoal(0);
        cycleGoal = new CycleGoal(1);
        goals.add(goldGoal);
        goals.add(expGoal);
        goals.add(cycleGoal);
    }

    public void setGoldGoal(int value) {
        goldGoal.setGoal(value);
    }

    public void setExpGoal(int value) {
        expGoal.setGoal(value);
    }

    public void setCycleGoal(int value) {
        cycleGoal.setGoal(value);
    }

    /**
     * 
     * @param g
     */

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        for (Goal temp: goals) {
            if (!temp.checkGoal(world)) {
                return false;
            }
        }
        return true;
    }

    public JSONObject toJSON() {
        JSONObject g = new JSONObject();
        g.put("goldGoal", this.goldGoal.getGoal());
        g.put("expGoal", this.expGoal.getGoal());
        g.put("cycleGoal", this.cycleGoal.getGoal());
        //g.put("bossGoal", this.bossGoal.getGoal());
        return g;
    }
}
