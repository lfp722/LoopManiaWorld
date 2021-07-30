package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class Soldier extends StaticEntity{
    private EntityAttribute attr;
    private final int inheritRate = 5;
    private Character general;

    public Soldier(SimpleIntegerProperty x, SimpleIntegerProperty y, Character ch) {
        super(x, y);
        attr = ch.getAttr().copy(inheritRate);
        general = ch;
    }
    
    

    public EntityAttribute getAttr() {
        return attr;
    }



    public void setAttr(EntityAttribute attr) {
        this.attr = attr;
    }



    /**
     * attack specific enemy
     * @param e
     */
    public void attack(Enemy e) {
        e.underAttack(attr.getAttack().get());
    }

    /**
     * be attacked and perform decrease in health
     * @param attack
     */
    public void underAttack(int attack) {
        if (attack > attr.getCurHealth().get() || attack == attr.getCurHealth().get()) {
            attr.getCurHealth().set(0);
            this.dead();
        }
        else {
            attr.getCurHealth().set(attr.getCurHealth().get() - attack);
        }
    }

    /**
     * perform a death of soldier
     * @return
     */
    public EntityAttribute dead() {
        general.remSoldier(this);
        this.destroy();
        return attr;
    }

    public JSONObject toJSON() {
        JSONObject soldier = new JSONObject();
        soldier.put("attr", attr.toJSON());
        soldier.put("x", getX());
        soldier.put("y", getY());
        return soldier;
    }

}
