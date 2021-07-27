package unsw.loopmania.goal;

import unsw.loopmania.*;

public class CycleGoal implements Goal{
    private int cycle;

    public CycleGoal(int cycle) {
        this.cycle = cycle;
    }

    public void setGoal(int value) {
        cycle = value;
    }

    public int getGoal() {
        return cycle;
    }

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.getCycle().get() >= cycle;
    }
}
