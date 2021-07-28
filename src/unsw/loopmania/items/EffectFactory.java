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
          }
          else {
               world.getCharacter().getStakeVampireBuff().set(1);
          }  
     } 

     public static void treeStump(LoopManiaWorld world, Enemy e) {
          if (e.getBoss()) {
               world.getCharacter().getTsBuff().set(3);
          }
          else {
               world.getCharacter().getTsBuff().set(1);
          }
     }

     public static void theOneRing(LoopManiaWorld world) {
          SimpleIntegerProperty curH = world.getCharacter().getAttr().getCurHealth();
          SimpleIntegerProperty maxH = world.getCharacter().getAttr().getHealth();
          curH.set(maxH.get());
          world.getCharacter().shouldExist().set(true);
     }
}
