package unsw.loopmania;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.*;

public class Equipped {
    private Helmet helmet;
    private Armour armour;
    private Shield shield;
    private Weapon weapon;
    private SimpleIntegerProperty equipDefense = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty equipAttack = new SimpleIntegerProperty(0);

    public void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
        equipDefense.set(equipDefense.get()+helmet.getDefense());
    }

    public void equipArmour(Armour armour) {
        this.armour = armour;
        equipDefense.set(equipDefense.get()+armour.getDefense());
    }

    public void equipShield(Shield shield) {
        this.shield = shield;
        equipDefense.set(equipDefense.get() + shield.getDefense());
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        equipAttack.set(weapon.getDamage());
    }

    public int getAttack() {
        if (weapon == null) {
            return 0;
        }
        else {
            return weapon.getDamage();
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void dropHelmet() {
        equipDefense.set(equipDefense.get()-helmet.getDefense());
        helmet = null;
    }

    public void dropArmour() {
        equipDefense.set(equipDefense.get()-armour.getDefense());
        armour = null;
    }

    public void dropShield() {
        equipDefense.set(equipDefense.get()-shield.getDefense());
        shield = null;
    }

    public void dropWeapon() {
        weapon = null;
        equipAttack.set(0);
    }

    public Helmet getHelmet() {
        return helmet;
    }

    public Shield getShield() {
        return shield;
    }

    public Armour getArmour() {
        return armour;
    }

    public void specialAttack(Enemy enemy, LoopManiaWorld world) {
        if (weapon != null) {
            weapon.specialEffect(enemy, world);
        }
    }

    public SimpleIntegerProperty getDefence() {
        return equipDefense;
    }

    public SimpleIntegerProperty getDamage() {
        return equipAttack;
    }


}
