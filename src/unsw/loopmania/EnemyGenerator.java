package unsw.loopmania;

import java.util.Random;

public class EnemyGenerator implements Generator {

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
        for (Building b: world.getBuildingEntities()) {
            //use instanceof here, change later
            if(!(b instanceof ZombiePit)) {
                return;
            }
            if (((new Random()).nextInt(100)) < 50) {
                b.specialEffect(world);
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
        for (Building b: world.getBuildingEntities()) {
            if(!(b instanceof VampireCastle)) {
                return;
            }
            if (((new Random()).nextInt(100)) < 50) {
                b.specialEffect(world);
            }
        }
        return; 
    }

}
