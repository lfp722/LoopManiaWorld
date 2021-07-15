package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    // TODO = implement other varieties of card than VampireCastleCard
    private Building building;
    private LoopManiaWorld world;

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public void setBuildingType(Building building){
        this.building = building;
    }

    public Building getBuildingType(){
        return this.building;
    }

    public void createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y, LoopManiaWorld world){
        world.convertCardToBuildingByCoordinates(this.getX(), this.getY(), this.getX(), this.getY())
    }
}
