package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.platform.console.shadow.picocli.CommandLine.Help;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.items.*;

public class EquippedTest {
    private LoopManiaWorld world;

    @Test
    public void testBasic() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Character ch = new Character(position, world);
        int originalAttack = ch.getAttr().getAttack().get();
        world.setCharacter(ch);
        Equipped eq = new Equipped(world);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Helmet helmet = new Helmet(x, y);
        Anduril anduril = new Anduril(x, y, false);
        TreeStump tree = new TreeStump(x, y, false);
        Armour armour = new Armour(x, y);
        Shield shield = new Shield(x, y);
        Staff staff = new Staff(x, y);
        Stake stake = new Stake(x, y);
        Sword sword = new Sword(x, y);
        TheOneRing ring = new TheOneRing(x, y, false);

        String expectedJSON = new String("{\"weapon\":{},\"shield\":{},\"ring\":{},\"helmet\":{},\"armour\":{}}");
        assertEquals(expectedJSON, eq.toJSON().toString());


        int expectedAttack = 0;
        int expectedDefence = 0;
        assertEquals(expectedAttack, eq.getAttack());
        assertEquals(expectedAttack, eq.getDamage().get());
        assertEquals(expectedDefence, eq.getDefence().get());
        expectedAttack = 3;
        eq.equipWeapon(sword);
        assertEquals(expectedAttack, eq.getAttack());
        assertEquals(expectedAttack, eq.getDamage().get());
        expectedAttack = 2;
        eq.equipWeapon(stake);
        assertEquals(expectedAttack, eq.getAttack());
        assertEquals(expectedAttack, eq.getDamage().get());
        expectedAttack = 1;
        eq.equipWeapon(staff);
        assertEquals(expectedAttack, eq.getAttack());
        assertEquals(expectedAttack, eq.getDamage().get());
        expectedAttack = 15;
        eq.equipWeapon(anduril);
        assertEquals(expectedAttack, eq.getAttack());
        assertEquals(expectedAttack, eq.getDamage().get());
        assertEquals(anduril, eq.getWeapon());

        expectedDefence = 8;
        eq.equipShield(shield);
        assertEquals(expectedDefence, eq.getDefence().get());
        assertEquals(shield, eq.getShield());
        expectedDefence = 16;
        eq.equipHelmet(helmet);
        assertEquals(expectedDefence, eq.getDefence().get());
        assertEquals(originalAttack * 0.8, world.getCharacter().getAttr().getAttack().get());
        assertEquals(helmet, eq.getHelmet());
        expectedDefence = 24;
        eq.equipArmour(armour);
        assertEquals(expectedDefence, eq.getDefence().get());
        assertEquals(armour, eq.getArmour());
        expectedDefence = 36;
        eq.equipShield(tree);
        assertEquals(expectedDefence, eq.getDefence().get());
        assertEquals(tree, eq.getShield());
        eq.equipRing(ring);
        assertEquals(ring, eq.getRing());

        String expected = new String("{\"weapon\":{\"level\":1,\"x\":0,\"y\":1,\"type\":\"Anduril\",\"raretype\":-1},\"shield\":{\"level\":1,\"x\":0,\"y\":1,\"type\":\"TreeStump\",\"raretype\":-1},\"ring\":{\"x\":0,\"y\":1,\"type\":\"TheOneRing\",\"raretype\":-1},\"helmet\":{\"level\":1,\"x\":0,\"y\":1,\"type\":\"Helmet\"},\"armour\":{\"level\":1,\"x\":0,\"y\":1,\"type\":\"Armour\"}}");
        assertEquals(expected, eq.toJSON().toString());
    }

}
