package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.condition.EnabledIf;

public class BattleSimulator implements Simulator{

    private void battle(List<Enemy> battleEnemies, List<Enemy> defeatedEnemies, Character ch) {
        while (!battleEnemies.isEmpty() && ch.shouldExist().get()) {
            for (Building b: getBuildingEntities()) {
                if (b instanceof Tower) {
                    Tower a = (Tower) b;
                    a.attackIfInRadius(this);
                }
            }
            Enemy target = battleEnemies.get(0);
            for (Soldier s: ch.getArmy()) {
                s.attack(target);
            }
            for (Enemy e: ch.getTranced()) {
                e.attack(target);
            }

            ch.attack(target);
            if (!target.shouldExist().get()) {
                battleEnemies.remove(target);
                defeatedEnemies.add(target);
            }

            for (Enemy e: battleEnemies) {
                if (ch.getArmy().isEmpty()) {
                    e.attack(ch);
                }

                Soldier brave = ch.getArmy().get(0);
                e.attack(brave, this);
            }
        }
    }

    @Override
    public List<Enemy> simulate(LoopManiaWorld world) {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        List<Enemy> battleEnemies = new ArrayList<>();
        boolean isBattle = false;
        Character character = world.getCharacter();


        for (Enemy e: world.getEnemies()){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (isBattle) {
                if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < e.getSupportRange()){
                    // fight...
                    battleEnemies.add(e);
                }
            }
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < e.getDetectRange()){
                // fight...
                battleEnemies.add(e);
                isBattle = true;
            }
        }

        battle(battleEnemies, defeatedEnemies, character);
        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            world.killEnemy(e);
        }
        
        return defeatedEnemies;

    }
    
}
