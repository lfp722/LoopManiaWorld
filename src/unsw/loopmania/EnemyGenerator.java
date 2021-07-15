package unsw.loopmania;

import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;

public class EnemyGenerator implements Generator {
    
    private SimpleIntegerProperty maxNum;

    public EnemyGenerator() {
    }

    @Override
    public void generate(LoopManiaWorld world) {
        generateSlug(world);
        generateZombie(world);
        generateVampire(world);
    }

    public void generateSlug(LoopManiaWorld world) {
        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return;
        }
        world.possiblySpawnEnemies();
    }
    /**
     * go throught the building list and check for specific building
     * spawn enemy under some probablility
     * find the path tile nearest to the building
     * put the enemy into the list
     * @param world
     */
    public void generateZombie(LoopManiaWorld world) {
        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return;
        }
        for (Building b: world.getBuildingEntities) {
            if(b.checkType() != "Zombie Pit") {
                return;
            }
            if (((new Random()).nextInt(100)) < prob) {
                Enemy e = b.EnemyProducer(world);
                world.addEnemy(e);
            }
        }
    }

    public void generateVampire(LoopManiaWorld world) {
        if (world.getCycle().get()%5 != 4) {
            return;
        }
        if (world.getMaxNumTotal().get() == world.getEnemies().size()) {
            return;
        }
        for (Building b: world.getBuildingEntities) {
            if(b.checkType() != "Vampire Castle") {
                return;
            }
            if (((new Random()).nextInt(100)) < prob) {
                Enemy e = b.EnemyProducer(world);
                world.addEnemy(e);
            }
        }
        return; 
    }

}
