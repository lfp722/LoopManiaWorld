package unsw.loopmania.items;

public class Shield extends Outfit {

    public Shield(boolean isEquipped, int level) {
        super(isEquipped, level);
        this.defense = 0;
        this.reduceRate = 1 - this.level * 0.15;
    }

    @Override
    public void setReduceRate() {
        this.reduceRate = 1 - this.level * 0.15;
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipShield(this);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().unequipShield(this);
    }
}
