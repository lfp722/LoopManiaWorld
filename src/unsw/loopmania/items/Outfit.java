package unsw.loopmania.items;

public class Outfit extends Equipment {

    private int defense;
    private double reduceRate;

    public Outfit(boolean isEquipped, int level) {
        super(isEquipped, level);
        this.ValueInGold = 250;
        this.defense = 0;
        this.reduceRate = 0;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public double getReduceRate() {
        return this.reduceRate;
    }

    public void setReduceRate() {
        this.reduceRate = 0.5;
    }
}
