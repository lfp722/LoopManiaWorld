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
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);


        Anduril i = new Anduril(x, y, false);
        int expectedPrice = 5000;
        int expectedAttack = 15;
        int expectedDefence = 20;
        assertEquals(expectedPrice, i.getValueInGold());
        assertEquals(expectedAttack, i.getAttack().get());
        assertEquals(expectedAttack, i.getDamage());
        i.levelUp();
        expectedPrice = 95000;
        expectedAttack = 20;
        assertEquals(expectedPrice, i.getValueInGold());
        assertEquals(expectedAttack, i.getAttack().get());
        assertEquals(expectedAttack, i.getDamage());
        String expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Anduril\",\"raretype\":-1}");
        assertEquals(expected, i.toJSON().toString());


        TreeStump t = new TreeStump(x, y, false);
        expectedPrice = 5000;
        assertEquals(expectedPrice, t.getValueInGold());
        assertEquals(expectedDefence, t.getDefenceProperty().get());
        assertEquals(expectedDefence, t.getDefense());
        t.levelUp();
        expectedPrice = 95000;
        expectedDefence = 30;
        assertEquals(expectedPrice, t.getValueInGold());
        assertEquals(expectedDefence, t.getDefenceProperty().get());
        assertEquals(expectedDefence, t.getDefense());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"TreeStump\",\"raretype\":-1}");
        assertEquals(expected, t.toJSON().toString());


        Armour a = new Armour(x, y);
        expectedPrice = 250;
        expectedDefence = 8;
        assertEquals(expectedPrice, a.getValueInGold());
        assertEquals(expectedDefence, a.getDefense());
        assertEquals(expectedDefence, a.getDefenceProperty().get());
        a.levelUp();
        expectedPrice = 10250;
        expectedDefence = 13;
        assertEquals(expectedPrice, a.getValueInGold());
        assertEquals(expectedDefence, a.getDefense());
        assertEquals(expectedDefence, a.getDefenceProperty().get());
        assertEquals(expectedPrice, a.getValueInGold());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Armour\"}");
        assertEquals(expected, a.toJSON().toString());



        Helmet h = new Helmet(x, y);
        expectedPrice = 250;
        expectedDefence = 8;
        assertEquals(expectedPrice, h.getValueInGold());
        assertEquals(expectedDefence, h.getDefense());
        assertEquals(expectedDefence, h.getDefenceProperty().get());
        assertEquals(expectedPrice, h.getValueInGold());
        h.levelUp();
        expectedPrice = 10250;
        expectedDefence = 13;
        assertEquals(expectedPrice, h.getValueInGold());
        assertEquals(expectedDefence, h.getDefense());
        assertEquals(expectedDefence, h.getDefenceProperty().get());
        assertEquals(expectedPrice, h.getValueInGold());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Helmet\"}");
        assertEquals(expected, h.toJSON().toString());



        Shield sh = new Shield(x, y);
        expectedPrice = 250;
        expectedDefence = 8;
        assertEquals(expectedPrice, sh.getValueInGold());
        assertEquals(expectedDefence, sh.getDefense());
        assertEquals(expectedDefence, sh.getDefenceProperty().get());
        assertEquals(expectedPrice, sh.getValueInGold());
        assertEquals(10250, sh.nextLevelUpPrice());
        sh.levelUp();
        expectedPrice = 10250;
        expectedDefence = 13;
        assertEquals(expectedPrice, sh.getValueInGold());
        assertEquals(expectedDefence, sh.getDefense());
        assertEquals(expectedDefence, sh.getDefenceProperty().get());
        assertEquals(expectedPrice, sh.getValueInGold());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Shield\"}");
        assertEquals(expected, sh.toJSON().toString());


        Staff staff = new Staff(x, y);
        expectedPrice = 400;
        expectedAttack = 1;
        assertEquals(expectedAttack, staff.getAttack().get());
        assertEquals(expectedAttack, staff.getDamage());
        assertEquals(expectedPrice, staff.getValueInGold());
        assertEquals(1, staff.nextDamage());
        staff.levelUp();
        expectedPrice = 40400;
        expectedAttack = 2;
        assertEquals(expectedAttack, staff.getAttack().get());
        assertEquals(expectedAttack, staff.getDamage());
        assertEquals(expectedPrice, staff.getValueInGold());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Staff\"}");
        assertEquals(expected, staff.toJSON().toString());


        Stake stake = new Stake(x, y);
        expectedPrice = 250;
        expectedAttack = 2;
        assertEquals(expectedAttack, stake.getAttack().get());
        assertEquals(expectedAttack, stake.getDamage());
        assertEquals(expectedPrice, stake.getValueInGold());
        assertEquals(expectedPrice, stake.currentPrice());
        assertEquals(10250, stake.getLevelUpPrice());
        stake.levelUp();
        expectedPrice = 10250;
        expectedAttack = 3;
        assertEquals(expectedAttack, stake.getAttack().get());
        assertEquals(expectedAttack, stake.getDamage());
        assertEquals(expectedPrice, stake.getValueInGold());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Stake\"}");
        assertEquals(expected, stake.toJSON().toString());


        Sword sword = new Sword(x, y);
        expectedPrice = 250;
        expectedAttack = 3;
        assertEquals(expectedAttack, sword.getAttack().get());
        assertEquals(expectedAttack, sword.getDamage());
        assertEquals(expectedPrice, sword.getValueInGold());
        sword.levelUp();
        expectedPrice = 10250;
        expectedAttack = 4;
        assertEquals(expectedAttack, sword.getAttack().get());
        assertEquals(expectedAttack, sword.getDamage());
        assertEquals(expectedPrice, sword.getValueInGold());
        expected = new String("{\"level\":2,\"x\":0,\"y\":1,\"type\":\"Sword\"}");
        assertEquals(expected, sword.toJSON().toString());


        Item doggie = new DoggieCoin(x, y, world.getDoggiePrice());
        expectedPrice = 5000;
        assertEquals(expectedPrice, doggie.getValueInGold());
        expected = new String("{\"x\":0,\"y\":1,\"type\":\"DoggieCoin\"}");
        assertEquals(expected, doggie.toJSON().toString());


        Item potion = new Potion(x, y);
        expectedPrice = 1000;
        assertEquals(expectedPrice, potion.getValueInGold());
        expected = new String("{\"x\":0,\"y\":1,\"type\":\"Potion\"}");
        assertEquals(expected, potion.toJSON().toString());


        Item gold = new Gold(x, y, expectedPrice);
        assertEquals(expectedPrice, gold.getValueInGold());
        expectedPrice = 40;
        gold.setValueInGold(40);
        assertEquals(expectedPrice, gold.getValueInGold());
        expected = new String("{\"valueingold\":40,\"x\":0,\"y\":1,\"type\":\"Gold\"}");
        assertEquals(expected, gold.toJSON().toString());
        

        TheOneRing ring = new TheOneRing(x, y, false);
        ch.getAttr().getCurHealth().set(0);
        ch.shouldExist().set(false);
        int expectedHealth = ch.getAttr().getHealth().get();
        ring.rebirth(world);
        assertFalse(!ch.shouldExist().get());
        assertEquals(expectedHealth, ch.getAttr().getCurHealth().get());
        expected = new String("{\"x\":0,\"y\":1,\"type\":\"TheOneRing\",\"raretype\":-1}");
        assertEquals(expected, ring.toJSON().toString());

    }

    @Test
    public void testConfusing() throws InterruptedException{
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        ElanMuske elan = new ElanMuske(position, 1);
        Slug slug = new Slug(position, 1);
        Character ch = new Character(position, world);
        int originalAttack = ch.getAttr().getAttack().get();
        world.setCharacter(ch);
        world.setMode(LoopManiaWorld.CONFUSING);
        assertEquals(0, world.getEquip().getSecondHealth().size());
        boolean se1 = false;
        boolean se2 = false;
        for (int i = 0; i < 100; i++) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(0);
            SimpleIntegerProperty y = new SimpleIntegerProperty(1);
            TheOneRing ring = new TheOneRing(x, y, true);
            
            world.getEquip().equipRing(ring);
            
            if (ring.getSecondType() == Item.DEFENCE &&
                    ring.getSecondValue() == EffectFactory.treeStump) {
                int expected = world.getEquip().getDefence().get() * 3;
                ring.secondEffect(world, elan);
                assertEquals(expected, world.getEquip().getDefence().get());
                se1 = true;
                ring.secondEffect(world, slug);
            } else if (ring.getSecondType() == Item.ATTACK &&
                    ring.getSecondValue() == EffectFactory.anduril) {
                ring.secondEffect(world, elan);
                assertEquals(originalAttack * 3, world.getCharacter().getAttr().getAttack().get());
                se2 = true;
                ring.secondEffect(world, slug);
            }
            world.getEquip().dropRing();
            
        }
        assertFalse(!se1 || !se2);
        se1 = false;
        se2 = false;
        for (int i = 0; i < 100; i++) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(0);
            SimpleIntegerProperty y = new SimpleIntegerProperty(1);
            Anduril ring = new Anduril(x, y, true);
            world.getEquip().equipWeapon(ring);
            if (ring.getSecondType() == Item.DEFENCE &&
                    ring.getSecondValue() == EffectFactory.treeStump) {
                
                int expected = world.getEquip().getDefence().get() * 3;
                ring.secondEffect(world, elan);
                assertEquals(expected, world.getEquip().getDefence().get());
                ring.secondEffect(world, slug);
                se1 = true;
            } else if (ring.getSecondType() == Item.HEALTH) {
                world.getCharacter().getAttr().getCurHealth().set(0);
                world.getCharacter().shouldExist().set(false);
                ring.secondEffect(world, elan);
                int expected = world.getCharacter().getAttr().getHealth().get();
                assertEquals(expected, world.getCharacter().getAttr().getCurHealth().get());
                assertFalse(!world.getCharacter().shouldExist().get());
                se2 = true;
            }
            world.getEquip().dropWeapon();
        }
        assertFalse(!se1 || !se2);
        se1 = false;
        se2 = false;
        for (int i = 0; i < 100; i++) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(0);
            SimpleIntegerProperty y = new SimpleIntegerProperty(1);
            TreeStump ring = new TreeStump(x, y, true);
            world.getEquip().equipShield(ring);
            if (ring.getSecondType() == Item.ATTACK &&
                    ring.getSecondValue() == EffectFactory.anduril) {
                ring.secondEffect(world, elan);
                assertEquals(originalAttack * 3, world.getCharacter().getAttr().getAttack().get());
                se2 = true;
            } else if (ring.getSecondType() == Item.HEALTH) {
                world.getCharacter().getAttr().getCurHealth().set(0);
                world.getCharacter().shouldExist().set(false);
                ring.secondEffect(world, elan);
                int expected = world.getCharacter().getAttr().getHealth().get();
                assertEquals(expected, world.getCharacter().getAttr().getCurHealth().get());
                assertFalse(!world.getCharacter().shouldExist().get());
                se2 = true;
            }
            world.getEquip().dropShield();
        }

    }
}
