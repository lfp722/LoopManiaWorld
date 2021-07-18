package unsw.loopmania.goal;
import unsw.loopmania.*;

public class ExpGoal implements Goal{
    private int exp;

    public ExpGoal(int exp) {
        this.exp = exp;
    }

    public void setGoal(int value) {
        exp = value;
    }
    
    @Override
    public boolean checkGoal(LoopManiaWorld world) {
        return world.getCharacter().getExp() >= exp;
    }
}
