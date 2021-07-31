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

        expectedDefence = 8;
        eq.equipShield(shield);
        assertEquals(expectedDefence, eq.getDefence().get());
        expectedDefence = 16;
        eq.equipHelmet(helmet);
        assertEquals(expectedDefence, eq.getDefence().get());
        assertEquals(originalAttack * 0.8, world.getCharacter().getAttr().getAttack().get());
        expectedDefence = 24;
        eq.equipArmour(armour);
        assertEquals(expectedDefence, eq.getDefence().get());
        expectedDefence = 36;
        eq.equipShield(tree);
        assertEquals(expectedDefence, eq.getDefence().get());
    }
}
