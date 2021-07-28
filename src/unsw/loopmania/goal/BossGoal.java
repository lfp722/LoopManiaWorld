package unsw.loopmania.goal;

import unsw.loopmania.LoopManiaWorld;

public class BossGoal implements Goal{
    private int boss;

    public BossGoal(int boss) {
        this.boss = boss;
    }

    public void setGoal(int value) {
        boss = value;
    }

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.getDefeatedBoss().get() >= boss;
    }
}
