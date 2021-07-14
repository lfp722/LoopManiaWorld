package unsw.loopmania.items;

public class Weapon extends Equipment {

    private int damage;

    public Weapon(boolean isEquipped, int level) {
        super(isEquipped, level);
        this.ValueInGold = 250;
        this.damage = 0;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
