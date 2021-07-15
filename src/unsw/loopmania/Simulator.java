package unsw.loopmania;

import java.util.List;
import java.util.Random;

public interface Simulator {

    public List<Enemy> simulate(LoopManiaWorld world);
    
}
