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
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Equipment i = new Anduril(x, y, false);
        int expectedPrice = 5000;
        assertEquals(expectedPrice, i.getValueInGold());
        i.levelUp();
        expectedPrice = 95000;
        assertEquals(expectedPrice, i.getValueInGold());
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
        Equipment a = new Armour(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, a.getValueInGold());
        a.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, a.getValueInGold());
        Equipment a = new Armour(x, y);
        expectedPrice = 250;
        assertEquals(expectedPrice, a.getValueInGold());
        a.levelUp();
        expectedPrice = 10250;
        assertEquals(expectedPrice, a.getValueInGold());

    }
}
