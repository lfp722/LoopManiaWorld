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
    
    public static void heal2(LoopManiaWorld world, List<Enemy> defeated, List<Soldier> dead) {
        int possibility = new Random().nextInt(100);
        if ((!defeated.isEmpty() || !dead.isEmpty()) && possibility < 30) {
            world.getCharacter().heal(30);
        }
    }

    public static void rage1(LoopManiaWorld world) {
        world.getCharacter().setRage();
    }

    // public static void rage2(LoopManiaWorld world) {
    //     int curH = world.getCharacter().getAttr().getCurHealth().get();
    //     int maxH = world.getCharacter().getAttr().getHealth().get();
    //     double ratio = (double)curH / maxH;
    //     if (ratio < 0.3) {
    //         world.getCharacter().getRage().set(2);
    //         for (Soldier i: world.getCharacter().getArmy()) {
    //             i.getRage().set(2);
    //         }
    //     }
    // }

    public static void counter(Character ch, Enemy e, int damage) {
        int possibility = new Random().nextInt(100);
        int discount = 5;
        if (possibility < 30) {
            e.underAttack(damage/discount);
        }
    }

    public static void counterMiss(Character ch, Enemy e, int damage) {
        int possibility = new Random().nextInt(100);
        int discount = 5;
        if (possibility < 20) {
            ch.startMiss();
        }
        else if (possibility < 40) {
            e.underAttack(damage/discount);
        }
    }
    


}
