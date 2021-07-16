package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

public class Building extends StaticEntity{

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    public Pair<Integer, Integer> getNearestPathTile(LoopManiaWorld world){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int x = this.getX() + i;
                int y = this.getY() + j;
                Pair<Integer, Integer> a = new Pair<>(x, y);
                if(world.getOrderedPath().contains(a)){
                    return a;
                }
            }
        }
        throw new Error("No pathtile near this building");
    }
}
