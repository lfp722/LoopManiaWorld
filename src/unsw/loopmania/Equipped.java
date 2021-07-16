package unsw.loopmania;
import unsw.loopmania.items.*;

public class Equipped {
    private Helmet helmet;
    private Armour armour;
    private Shield shield;
    private Weapon weapon;
    private Character ch;

    public Equipped() {        
    }

    public void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public void equipArmour(Armour armour) {
        this.armour = armour;
    }

    public void equipShield(Shield shield) {
        this.shield = shield;
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
        ch.getBag().add(helmet);
        helmet = null;
    }

    public void dropArmour() {
        ch.getBag().add(armour);
        armour = null;
    }

    public void dropShield() {
        ch.getBag().add(shield);
        shield = null;
    }

    public void dropWeapon() {
        ch.getBag().add(weapon);
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


}
