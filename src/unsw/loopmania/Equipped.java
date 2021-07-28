package unsw.loopmania;


import java.util.ArrayList;
import java.util.List;

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
    private SimpleIntegerProperty confusingAttack = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty confusingDefence = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty equipWeaponAttack = new SimpleIntegerProperty(0);
    private List<Item> secondAttack = new ArrayList<>();
    private List<Item> secondDefence = new ArrayList<>();
    private List<Item> secondHealth = new ArrayList<>();
    private LoopManiaWorld world;
    private TheOneRing ring;
    private SimpleIntegerProperty Tsbuff;

    public Equipped(LoopManiaWorld world) {
        this.world = world;
        Tsbuff = new SimpleIntegerProperty(1);
        equipDefense.bind(Bindings.createIntegerBinding(()->equipArmourDefence.get()+equipHelmetDefence.get()+equipShieldDefence.get()*Tsbuff.get()+confusingDefence.get(), equipArmourDefence, equipHelmetDefence, equipShieldDefence, Tsbuff, confusingDefence));
        equipAttack.bind(Bindings.createIntegerBinding(()->equipWeaponAttack.get()+confusingAttack.get(), equipWeaponAttack, confusingAttack));
    }

    /**
     * equipment implementation for different type
     * @param equipment an equipment to be equipped
     */
    public void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
        equipHelmetDefence.bind(helmet.getDefenceProperty());
        world.getCharacter().getDebuff().set(0.8);
        checkSecondAdd(helmet);
    }

    public void equipArmour(Armour armour) {
        this.armour = armour;
        equipArmourDefence.bind(armour.getDefenceProperty());
        checkSecondAdd(armour);
    }

    public void equipShield(Shield shield) {
        this.shield = shield;
        equipShieldDefence.bind(shield.getDefenceProperty());
        checkSecondAdd(shield);
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        equipWeaponAttack.bind(weapon.getAttack());
        checkSecondAdd(weapon);
    }

    public void equipRing(TheOneRing ring) {
        this.ring = ring;
        checkSecondAdd(ring);
    }

    public TheOneRing getRing() {
        return ring;
    }

    public void dropRing() {
        if (ring == null) {
            return;
        }
        ring.destroy();
        checkSecondRem(ring);
        ring = null;
    }

    /**
     * get the weapon's attack
     * @return
     */
    public int getAttack() {
        return equipAttack.get();
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
        checkSecondRem(helmet);
        helmet = null;
    }

    public void dropArmour() {
        equipArmourDefence.unbind();
        equipArmourDefence.set(0);
        if (armour == null) {
            return;
        }
        armour.destroy();
        checkSecondRem(armour);
        armour = null;
    }

    public void dropShield() {
        equipShieldDefence.unbind();
        equipShieldDefence.set(0);
        if (shield == null) {
            return;
        }
        shield.destroy();
        System.out.println(confusingAttack.get());
        checkSecondRem(shield);
        System.out.println(confusingAttack.get());
        shield = null;
    }

    public void dropWeapon() {       
        equipWeaponAttack.unbind();
        equipWeaponAttack.set(0);
        if (weapon == null) {
            return;
        }
        weapon.destroy();
        System.out.println(confusingDefence.get());
        checkSecondRem(weapon);
        System.out.println(confusingDefence.get());
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

        if (!secondAttack.isEmpty()) {
            for (Item i: secondAttack) {
                i.secondEffect(world, enemy);
            }
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
        if (shield == null && secondDefence.isEmpty()) {
            return;
        }
        if (shield == null) {
            for (Item i: secondDefence) {
                i.secondEffect(world, e);
            }
            return;
        }
        shield.specialEffect(e, world);
 
    }

    public void checkSecondAdd(Item i) {
        if (!world.isConfusing()) {
            return;
        }
        if (!i.isRare()) {
            return;
        }
        if (i.getSecondType() == Item.DEFAULT) {
            return;
        }

        if (i.getSecondType() == Item.DEFENCE) {
            confusingDefence.set(confusingDefence.get()+i.getSecondValue());
            secondDefence.add(i);
        }
        else if (i.getSecondType() == Item.ATTACK) {
            confusingAttack.set(confusingAttack.get()+i.getSecondValue());
            secondAttack.add(i);
        }
        else {
            secondHealth.add(i);
        }
    }

    public void checkSecondRem(Item i) {
        if (!world.isConfusing()) {
            return;
        }
        if (!i.isRare()) {
            return;
        }
        if (i.getSecondType() == Item.DEFAULT) {
            return;
        }

        if (i.getSecondType() == Item.DEFENCE) {
            confusingDefence.set(confusingDefence.get()-i.getSecondValue());
            secondDefence.remove(i);
        }
        else if (i.getSecondType() == Item.ATTACK) {
            confusingAttack.set(confusingAttack.get()-i.getSecondValue());
            secondAttack.remove(i);
        }
        else {
            secondHealth.remove(i);
        }
    }

    public List<Item> getSecondHealth() {
        return secondHealth;
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
            e.put("helmet", new JSONObject());
        }
        if (this.weapon != null) {
            e.put("weapon", this.weapon.toJSON());
        } else {
            e.put("weapon", new JSONObject());
        }
        if (this.shield != null) {
            e.put("shield", this.shield.toJSON());
        } else {
            e.put("shield", new JSONObject());
        }
        if (this.ring != null) {
            e.put("ring", this.ring.toJSON());
        } else {
            e.put("ring", new JSONObject());
        }
        return e;
    }

}
