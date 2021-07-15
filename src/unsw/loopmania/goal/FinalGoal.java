package unsw.loopmania.goal;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.*;

public class FinalGoal implements Goal{
    private List<Goal> goals;

    public FinalGoal() {
        goals = new ArrayList<>();
    }

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
