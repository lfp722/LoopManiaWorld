package unsw.loopmania.goal;

import unsw.loopmania.*;

public class FinalGoal implements Goal{

    private GoldGoal goldGoal;
    private ExpGoal expGoal;
    private CycleGoal cycleGoal;
    private BossGoal bossGoal;

    public FinalGoal() {
        goldGoal = new GoldGoal(0);
        expGoal = new ExpGoal(0);
        cycleGoal = new CycleGoal(1);
        bossGoal = new BossGoal(0);
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

    public void setBossGoal(int value) {
        bossGoal.setGoal(value);
    }

    /**
     * 
     * @param g
     */

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return bossGoal.checkGoal(world) && goldGoal.checkGoal(world) && expGoal.checkGoal(world) && cycleGoal.checkGoal(world);
    }
}
