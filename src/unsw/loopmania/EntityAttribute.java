package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class EntityAttribute {
    private SimpleIntegerProperty attack;
    private SimpleIntegerProperty defence;
    private SimpleIntegerProperty health;
    private SimpleIntegerProperty cur_health;

    public EntityAttribute() {
        this.attack = new SimpleIntegerProperty();
        this.defence = new SimpleIntegerProperty();
        this.health = new SimpleIntegerProperty();
        this.cur_health = new SimpleIntegerProperty();
    }

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

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getAttack(){
        return attack;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getDefence(){
        return defence;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getHealth(){
        return health;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getCurHealth() {
        return cur_health;
    }

    /**
     * link the attribute between character and a soldier, with some discount
     * @param discount
     * @return
     */
    public EntityAttribute copy(int discount) {
        return new EntityAttribute(attack.get() / discount + 1, defence.get() / discount + 1, health.get() / discount + 1);
    } 

    public JSONObject toJSON() {
        JSONObject attr = new JSONObject();
        attr.put("health", health.get());
        attr.put("curHealth", cur_health.get());
        attr.put("attack", attack.get());
        attr.put("defence", defence.get());
        return attr;
    }
}
