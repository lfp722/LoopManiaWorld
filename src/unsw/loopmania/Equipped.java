package unsw.loopmania;

import org.json.JSONObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.*;

public class Equipped {
    private Helmet helmet;
    private Armour armour;
    private Shield shield;
    private Weapon weapon;
    private SimpleIntegerProperty equipDefense = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty equipHelmetDefence = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty equipArmourDefence = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty equipShieldDefence = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty equipAttack = new SimpleIntegerProperty(0);
    private LoopManiaWorld world;
    private TheOneRing ring;
    private SimpleIntegerProperty Tsbuff;

    public Equipped(LoopManiaWorld world) {
        this.world = world;
        Tsbuff = new SimpleIntegerProperty(1);
        equipDefense.bind(Bindings.createIntegerBinding(()->equipArmourDefence.get()+equipHelmetDefence.get()+equipShieldDefence.get()*Tsbuff.get(), equipArmourDefence, equipHelmetDefence, equipShieldDefence, Tsbuff));
    }

    /**
     * equipment implementation for different type
     * @param equipment an equipment to be equipped
     */
    public void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
        equipHelmetDefence.bind(helmet.getDefenceProperty());
        world.getCharacter().getDebuff().set(0.8);
    }

    public void equipArmour(Armour armour) {
        this.armour = armour;
        equipArmourDefence.bind(armour.getDefenceProperty());
    }

    public void equipShield(Shield shield) {
        this.shield = shield;
        equipShieldDefence.bind(shield.getDefenceProperty());
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        equipAttack.bind(weapon.getAttack());
    }

    public void equipRing(TheOneRing ring) {
        this.ring = ring;
    }

    public TheOneRing getRing() {
        return ring;
    }

    public void dropRing() {
        if (ring == null) {
            return;
        }
        ring.destroy();
        ring = null;
    }

    /**
     * get the weapon's attack
     * @return
     */
    public int getAttack() {
        if (weapon == null) {
            return 0;
        }
        else {
            return weapon.getDamage();
        }
    }

    /**
     * getter
     * @return
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * unequip an equipment of different type
     */
    public void dropHelmet() {
        equipHelmetDefence.unbind();
        equipHelmetDefence.set(0);
        if (helmet == null) {
            return;
        }
        helmet.destroy();
        helmet = null;
    }

    public void dropArmour() {
        equipArmourDefence.unbind();
        equipArmourDefence.set(0);
        if (armour == null) {
            return;
        }
        armour.destroy();
        armour = null;
    }

    public void dropShield() {
        equipShieldDefence.unbind();
        equipShieldDefence.set(0);
        if (shield == null) {
            return;
        }
        shield.destroy();
        shield = null;
    }

    public void dropWeapon() {       
        equipAttack.unbind();
        equipAttack.set(0);
        if (weapon == null) {
            return;
        }
        weapon.destroy();
        weapon = null;
    }

    /**
     * getter
     * @return
     */
    public Helmet getHelmet() {
        return helmet;
    }

    /**
     * getter
     * @return
     */
    public Shield getShield() {
        return shield;
    }

    /**getter */
    public Armour getArmour() {
        return armour;
    }

    /**
     * special effect of a weapon
     * @param enemy
     * @param world
     */
    public void specialAttack(Enemy enemy, LoopManiaWorld world) {
        if (weapon != null) {
            weapon.specialEffect(enemy, world);
        }
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getDefence() {
        return equipDefense;
    }

    /**
     * getter
     * @return
     */
    public SimpleIntegerProperty getDamage() {
        return equipAttack;
    }

    public SimpleIntegerProperty getTsBuff() {
        return Tsbuff;
    }

    public void specialDefence(Enemy e, LoopManiaWorld world) {
        if (shield == null) {
            return;
        }
        shield.specialEffect(e, world);
    }

    public JSONObject toJSON() {
        JSONObject e = new JSONObject();
        if (this.armour != null) {
            e.put("armour", this.armour.toJSON());
        } else {
            e.put("armour", new JSONObject());
        }
        if (this.helmet != null) {
            e.put("helmet", this.helmet.toJSON());
        } else {
            e.put("armour", new JSONObject());
        }
        if (this.weapon != null) {
            e.put("weapon", this.weapon.toJSON());
        } else {
            e.put("armour", new JSONObject());
        }
        if (this.shield != null) {
            e.put("shield", this.shield.toJSON());
        } else {
            e.put("armour", new JSONObject());
        }
        if (this.ring != null) {
            e.put("ring", this.ring.toJSON());
        } else {
            e.put("ring", new JSONObject());
        }
        return e;
    }

}
