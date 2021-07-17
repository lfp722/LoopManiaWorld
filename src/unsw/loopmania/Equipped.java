package unsw.loopmania;
import unsw.loopmania.items.*;

public class Equipped {
    private Helmet helmet;
    private Armour armour;
    private Shield shield;
    private Weapon weapon;
    private int equipDefense = 0;

    public void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
        equipDefense += helmet.getDefense();
    }

    public void equipArmour(Armour armour) {
        this.armour = armour;
        equipDefense += armour.getDefense();
    }

    public void equipShield(Shield shield) {
        this.shield = shield;
        equipDefense += shield.getDefense();
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
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
        helmet = null;
    }

    public void dropArmour() {
        armour = null;
    }

    public void dropShield() {
        shield = null;
    }

    public void dropWeapon() {
        weapon = null;
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

    public void specialAttack(Enemy enemy, Character ch) {
        if (weapon != null) {
            weapon.specialEffect(enemy, ch);
        }
    }

    public int getDefence() {
        return equipDefense;
    }


}
