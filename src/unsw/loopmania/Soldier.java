package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Soldier extends StaticEntity{
    private EntityAttribute attr;

    public Soldier(SimpleIntegerProperty x, SimpleIntegerProperty y, Character character) {
        super(x, y);
        attr = new EntityAttribute(5,0,10);
    }

}
