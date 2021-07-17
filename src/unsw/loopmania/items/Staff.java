package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.animation.KeyFrame;
import javafx.beans.binding.Bindings;
import java.lang.Math;
import java.util.Random;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;
import unsw.loopmania.*;
import unsw.loopmania.Character;
/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {
    private SimpleDoubleProperty chanceTrance;
    private Timeline tranceTimeFrame;
    private SimpleIntegerProperty tranceTimeAmount;
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.ValueInGold.set(400);
        damage.set(6);
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

    // @Override
    // public double nextDamage() {
    //     return (double) 0.85 * this.level.get() + 1;
    // }
    // public double getChanceTrance(){
    //     return this.chanceTrance.get();
    // }
    // public double nextChanceTrance(){
    //     return (double) this.level.get() * 0.15;
    // }
    
    @Override
    public void specialEffect(Enemy enemy, LoopManiaWorld world) {
        if (new Random().nextInt(100) > 20) {
            return;
        }
        world.getCharacter().getTranced().add(enemy);
        world.getEnemies().remove(enemy);
        System.out.println("You have got an tranced enemy");
    }

    // public void reverseEffect(Enemy enemy) {
    //     enemy.becomeEnemy();
    // }
}
