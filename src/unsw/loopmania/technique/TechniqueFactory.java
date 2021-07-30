package unsw.loopmania.technique;

import java.util.List;
import java.util.Random;

import unsw.loopmania.Character;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Soldier;

public class TechniqueFactory {
    public static void heal1(LoopManiaWorld world, List<Enemy> defeated) {
        int possibility = new Random().nextInt(100);
        if (!defeated.isEmpty() && possibility < 20) {
            world.getCharacter().heal(20);
        }
    }


    public static void rage1(LoopManiaWorld world) {
        world.getCharacter().setRage();
    }


    public static void counterMiss(Character ch, Enemy e, int damage) {
        int possibility = new Random().nextInt(100);
        if (possibility < 20) {
            ch.startMiss();
        }
    }
    


}
