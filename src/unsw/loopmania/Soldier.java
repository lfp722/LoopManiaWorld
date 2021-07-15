package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class Soldier extends StaticEntity{
    private EntityAttribute attr;
    private final int inheritRate = 5;
    private Character general;

    public Soldier(SimpleIntegerProperty x, SimpleIntegerProperty y, Character character) {
        x.bind(character.getX());
        super(x, y);
        attr = character.getAttr().copy(inheritRate);
        general = character;
    }
    
    public int getAttack() {
        return attr.getAttack().get();
    }

    public void underAttack(int attack) {
        if (attack > attr.getCurHealth().get() || attack == attr.getCurHealth().get()) {
            this.dead();
        }
        else {
            attr.getCurHealth().subtract(attack);
        }
    }

    //TODO: remove from battle list
    public EntityAttribute dead() {
        general.remSoldier(this);
        this.destroy();
        return attr;
    }

}
