package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.items.*;

public class ItemTest {
    private LoopManiaWorld world;

    @Test
    public void testBasic() {
        this.world = Helper.createWorld();
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Anduril i = new Anduril(x, y, false);
        int expectedPrice = 5000;
        int expectedAttack = 15;
        assertEquals(expectedPrice, i.getValueInGold());
        assertEquals(expectedAttack, i.getAttack().get());
        assertEquals(expectedAttack, i.getDamage());
        i.levelUp();
        expectedPrice = 95000;
        expectedAttack = 20;
        assertEquals(expectedPrice, i.getValueInGold());
        assertEquals(expectedAttack, i.getAttack().get());
        assertEquals(expectedAttack, i.getDamage());
        Equipment t = new TreeStump(x, y, false);
        expectedPrice = 5000;
        assertEquals(expectedPrice, t.getValueInGold());
        t.levelUp();
        expectedPrice = 95000;
        assertEquals(expectedPrice, t.getValueInGold());
        Equipment a = new Armour(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, a.getValueInGold());
        a.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, a.getValueInGold());
        Equipment h = new Helmet(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, h.getValueInGold());
        h.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, h.getValueInGold());
        Equipment sh = new Shield(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, sh.getValueInGold());
        sh.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, sh.getValueInGold());
        Equipment staff = new Staff(x, y);
        expectedPrice = 400;
        assertEquals(expectedPrice, staff.getValueInGold());
        staff.levelUp();
        expectedPrice = 40400;
        assertEquals(expectedPrice, staff.getValueInGold());
        Equipment stake = new Stake(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, stake.getValueInGold());
        stake.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, stake.getValueInGold());
        Equipment sword = new Sword(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, sword.getValueInGold());
        sword.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, sword.getValueInGold());

        Item doggie = new DoggieCoin(x, y, world.getDoggiePrice());
        expectedPrice = 5000;
        assertEquals(expectedPrice, doggie.getValueInGold());
        Item potion = new Potion(x, y);
        expectedPrice = 1000;
        assertEquals(expectedPrice, potion.getValueInGold());
        Item gold = new Gold(x, y, expectedPrice);
        assertEquals(expectedPrice, gold.getValueInGold());
        expectedPrice = 40;
        gold.setValueInGold(40);
        assertEquals(expectedPrice, gold.getValueInGold());
        String expected = new String("{\"valueingold\":40,\"x\":0,\"y\":1,\"type\":\"Gold\"}");
        assertEquals(expected, gold.toJSON().toString());
    }
}
