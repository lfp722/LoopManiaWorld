package unsw.loopmania;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

public class Building extends StaticEntity{

    private EnemyProducer enemyProducer;

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    public Pair<Integer, Integer> getNearestPathTile(LoopManiaWorld world){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int x = this.getX() + i;
                int y = this.getY() + j;
                Pair<Integer, Integer> a = new Pair<Integer, Integer>(x, y);
                if(world.isInPath(a)){
                    return a;
                }
            }
        }
        throw new Error("No pathtile near this building");
    }

    public Enemy applyEnemyProducer(LoopManiaWorld world) {
        return enemyProducer.enemyProducer(world);
    }

    public void specialEffect(LoopManiaWorld world) {
<<<<<<< HEAD

=======
>>>>>>> d30da675d54be37139e0a71098e4167f8f8926e3
    }

}
