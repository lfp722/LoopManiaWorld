package unsw.loopmania.items;
import java.lang.Math;
public class Helmet extends Outfit {

    public Helmet(boolean isEquipped, int level) {
        super(isEquipped, level);
        this.defense = 0;
        this.reduceRate = 1 /(Math.sqrt(this.level));
    }

    @Override
    public void setReduceRate() {
        this.reduceRate = 1 /(Math.sqrt(this.level));
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipHelmet(this);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().unequipHelmet(this);
    }
}
