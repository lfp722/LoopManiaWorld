package unsw.loopmania.goal;

import unsw.loopmania.*;

public class CycleGoal implements Goal{
    private int cycle;

    public CycleGoal(int cycle) {
        this.cycle = cycle;
    }

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.getCycle() == cycle;
    }
}
