package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;

public class EffectFactory {
     public static int anduril = 15;
     public static int treeStump = 20;

     public static void anduril(LoopManiaWorld world, Enemy e) {
          if (e.getBoss()) {
               world.getCharacter().getStakeVampireBuff().set(3);
               world.getBattleStatus().add("Attacking a boss, triple attacking buff triggered!\n");
          }
          else {
               world.getCharacter().getStakeVampireBuff().set(1);
               world.getBattleStatus().add("Attacking a normal enemy, triple attacking buff cancelled!\n");
          }  
     } 

     public static void treeStump(LoopManiaWorld world, Enemy e) {
          if (e.getBoss()) {
               world.getCharacter().getTsBuff().set(3);
               world.getBattleStatus().add("Attacked by a boss, triple defending buff triggered!\n");
          }
          else {
               world.getCharacter().getTsBuff().set(1);
               world.getBattleStatus().add("Attacked by normal enemy, triple attacking buff cancelled!\n");
          }
     }

     public static void theOneRing(LoopManiaWorld world) {
          SimpleIntegerProperty curH = world.getCharacter().getAttr().getCurHealth();
          SimpleIntegerProperty maxH = world.getCharacter().getAttr().getHealth();
          curH.set(maxH.get());
          world.getCharacter().shouldExist().set(true);

          String curHe = world.getCharacter().getAttr().getCurHealth().get() + "/" + world.getCharacter().getAttr().getHealth().get();
          world.getBattleStatus().add("The one ring has saved your life! Come on and revenge! Your health is: " + curHe + "!\n");
     }
}
