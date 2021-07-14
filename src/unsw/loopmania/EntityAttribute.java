package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class EntityAttribute {
    private SimpleIntegerProperty attack;
    private SimpleIntegerProperty defence;
    private SimpleIntegerProperty health;
    private SimpleIntegerProperty cur_health;

    public EntityAttribute(int attack, int defence, int health){
        this.attack = new SimpleIntegerProperty();
        this.defence = new SimpleIntegerProperty();
        this.health = new SimpleIntegerProperty();
        this.cur_health = new SimpleIntegerProperty();
        
        this.attack.set(attack);
        this.defence.set(defence);
        this.health.set(health);
        this.cur_health.set(health);
    }

    public SimpleIntegerProperty getAttack(){
        return attack;
    }

    public SimpleIntegerProperty getDefence(){
        return defence;
    }

    public SimpleIntegerProperty getHealth(){
        return health;
    }

    public SimpleIntegerProperty getCurHealth() {
        return cur_health;
    }
}
