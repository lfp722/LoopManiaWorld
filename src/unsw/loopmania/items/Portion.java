package unsw.loopmania.items;

public class Portion extends Item{
    private double recoverRate;

    public Portion(Character owner){
        super();
        this.recoverRate = 0.2;
        this.ValueInGold = 100;
        this.owner = owner;
    }

    public double getRecoverRate() {
        return this.recoverRate;
    }

    public void setRecoverRate(float recoverRate) {
        this.recoverRate = recoverRate;
    }

    public void recoverHealth() {
        this.owner.curHealth += this.owner.maxHealth * this.recoverRate;
        if (this.owner.curHealth >= this.owner.maxHealth) {
            this.owner.curHealth = this.owner.maxHealth;
        }
    }

    @Override
    public void use(){
        if (this.owner != null) {
            this.recoverHealth();
            return;
        }
        throw new RuntimeException("Portion_Error == USE: the owner is not set!");
    }

    @Override
    public void abandon(){
        throw new RuntimeException("Portion_Error == DROP: the Portion cannot be dropped!");
    }
}