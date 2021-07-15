package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class Soldier extends StaticEntity{
    private EntityAttribute attr;
    private final int inheritRate = 5;
    private Character general;

    public Soldier(SimpleIntegerProperty x, SimpleIntegerProperty y, Character ch) {
        super(x, y);
        attr = ch.getAttr().copy(inheritRate);
        general = ch;
        x.bind(ch.x());
        y.bind(ch.y());
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