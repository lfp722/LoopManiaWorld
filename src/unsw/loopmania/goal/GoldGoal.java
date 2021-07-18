package unsw.loopmania.goal;

import unsw.loopmania.*;

public class GoldGoal implements Goal{
    private int gold;

    public GoldGoal(int gold) {
        this.gold = gold;
    }

    public void setGoal(int value) {
        gold = value;
    }

    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.getCharacter().getGold() == gold;
    }

    
}
