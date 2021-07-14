package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public class Building extends StaticEntity{
    private IntegerProperty x, y;

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
}
