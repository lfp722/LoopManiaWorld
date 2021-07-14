package unsw.loopmania.items;

public class Item {
    protected int ValueInGold;
    protected Character owner;


    public Item(){
        this.ValueInGold = 0;
        this.owner = null;
    }

    public void use() {
        return;
    }

    public void abandon() {
        return;
    }

    public int getValueInGold() {
        return this.ValueInGold;
    }

    public void setValueInGold(int ValueInGold) {
        this.ValueInGold = ValueInGold;
    }

    public Character getOwner() {
        return this.owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }
}