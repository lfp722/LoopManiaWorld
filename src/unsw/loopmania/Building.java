package unsw.loopmania;

import org.javatuples.Pair;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class Building extends StaticEntity{

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    /**
     * get a path tile position next to a building, for the enemy to produce
     * @param world
     * @return Pair<Integer, Integer> position coodinates of the tile near an building
     */
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

    /**
     * to be overriden
     * @param world
     */
    public void specialEffect(LoopManiaWorld world) {
    }

    public JSONObject toJSON() {
        return null;
    }

}
