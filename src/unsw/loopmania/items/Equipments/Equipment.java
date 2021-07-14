package unsw.loopmania.items.Equipments;
import java.lang.Math;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.items.Item;
public class Equipment extends Item{
    protected boolean isEquipped;
    protected int level;
    protected int levelUpPrice;

    public int getLevelUpPrice() {
        return this.levelUpPrice;
    }

    public void setLevelUpPrice() {
        this.levelUpPrice = (int) (Math.pow(100 * this.level, 2) - 150);
    }

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean isEquipped){
        super(x, y);
        this.isEquipped = isEquipped;
        this.level = 1;
        this.ValueInGold = 250;
    }

    public boolean isEquipped() {
        return this.isEquipped;
    }

    public void setEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level += 1;
    }


    public void equip() {
        this.owner.getEquip().equip(this);
    }
    public void unequip() {
        this.owner.getEquip().unequip(this);
    }

    @Override
    public void use(){
        if (this.owner != null) {
            if (!isEquipped) {
                setEquipped(true);
                this.equip();
            }
            else {
                setEquipped(false);
                this.unequip();
            }
            return;
        }
        throw new IllegalArgumentException("Equipment_Error == USE: the owner is not set!");
    }

    @Override
    public void abandon(){
        throw new IllegalArgumentException("Equipment_Error == ABANDON: the Equipment cannot be abandoned!");
    }
}