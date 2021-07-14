package unsw.loopmania.items;

public class Armour extends Outfit {

    public Armour(boolean isEquipped, int level) {
        super(isEquipped, level);
        this.defense = 100;
        this.reduceRate = 0.5;
    }

    @Override
    public void equip() {
        this.owner.getEquip().equipArmour(this);
    }

    @Override
    public void unequip() {
        this.owner.getEquip().unequipArmour(this);
    }
}
