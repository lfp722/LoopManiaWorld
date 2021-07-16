package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    private static final int SimpleIntegerProperty = 0;
    private String building;
    private LoopManiaWorld world;

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public void setBuildingType(Building building){
        this.building = building.toString();
    }

    public String getBuildingType(){
        return this.building;
    }

}
