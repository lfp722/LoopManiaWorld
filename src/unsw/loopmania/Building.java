package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import java.util.ArrayList;

public class Building extends StaticEntity{
    private IntegerProperty x, y;
    private PathTile pt;

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x,y);
    }

    public SimpleIntegerProperty getX(){
        return this.x;
    }

    public SimpleIntegerProperty getY(){
        return this.y;
    }
    
    public void setX(SimpleIntegerProperty x){
        this.x = x;
    }

    public void setY(SimpleIntegerProperty y){
        this.y = y;
    }

    public PathTile getPathTileNearBuilding(){
        ArrayList<PathTile> a = new ArrayList<>();
        int x = this.getX();
        int y = this.getY();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1;j++){
                PathTile b = new PathTile(x, y);
            }
        }
        return a;
    }
}
