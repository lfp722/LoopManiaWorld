package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.binding.Bindings;
import java.lang.Math;
import java.util.Random;

import unsw.loopmania.*;
/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {
    private SimpleIntegerProperty chanceTrance;
    public static final int initialPrice = 400;
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.chanceTrance = new SimpleIntegerProperty(20);
        //this.ValueInGold.set(400);
        //damage.set(6);
        // this.chanceTrance.set(0.15 * this.level.get());

        // this.chanceTrance.bind(Bindings.createDoubleBinding(()->this.nextChanceTrance(),this.level));

        // this.tranceTimeAmount.set(2);

        // this.tranceTimeFrame = new Timeline(new KeyFrame(Duration.seconds(this.tranceTimeAmount.get())));
        // this.tranceTimeFrame.setCycleCount(Animation.INDEFINITE);
        // this.tranceTimeFrame.setOnFinished(event -> this.reverseEffect());
    }
    

    // @Override
    // public int nextLevelUpPrice() {
    //     return (int) Math.pow(200 * this.level.get(), 2) - 200;
    // }


    // @Override
    // public void hit() {
    //     super.hit();
    //     double chanceTrance = Math.random();
    //     if (chanceTrance <= this.chanceTrance.get()) {
    //         specialEffect();
    //         this.tranceTimeFrame.playFromStart();
    //     }
    //      //instance of random class
        
    // }

    @Override
    public int nextDamage() {
        return (int) 0.85 * this.level.get() + 1;
    }
    // public double getChanceTrance(){
    //     return this.chanceTrance.get();
    // }
    // public double nextChanceTrance(){
    //     return (double) this.level.get() * 0.15;
    // }
    
    @Override
    public void specialEffect(Enemy enemy, LoopManiaWorld world) {
        if (new Random().nextInt(100) > this.chanceTrance.get()) {
            return;
        }
        world.getCharacter().getTranced().add(enemy);
        world.getEnemies().remove(enemy);
        System.out.println("You have got an tranced enemy");
    }

    @Override
    public int nextLevelUpPrice() {
        return (int) Math.pow((200 * (this.level.get())),2) + 400;
    }

    @Override
    public int currentPrice() {
        return (int) Math.pow((200 * (this.level.get() - 1)),2) + 400;
    }
    // public void reverseEffect(Enemy enemy) {
    //     enemy.becomeEnemy();
    // }
}
