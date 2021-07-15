package unsw.loopmania.goal;
import unsw.loopmania.*;

public class ExpGoal implements Goal{
    private int exp;

    public ExpGoal(int exp) {
        this.exp = exp;
    }
    
    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.getCharacter().getExp() == exp;
    }
}
