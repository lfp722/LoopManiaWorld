package unsw.loopmania.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unsw.loopmania.*;

public class FinalGoal implements Goal{
    private List<Goal> goals;

    public FinalGoal() {
        goals = new ArrayList<>();
        int gold = new Random().nextInt(200000)+100000;
        goals.add(new GoldGoal(gold));
        int exp = new Random().nextInt(400000)+200000;
        goals.add(new ExpGoal(exp));
        int cycle = new Random().nextInt(20)+10;
        goals.add(new CycleGoal(cycle));
    }

    /**
     * 
     * @param g
     */
    public void addGoal(Goal g) {
        goals.add(g);
    }

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        for (Goal temp: goals) {
            if (!temp.checkGoal(world)) {
                return false;
            }
        }
        return true;
    }
}
