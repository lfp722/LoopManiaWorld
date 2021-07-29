package newtest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.*;

public class EnemyTest {

    private LoopManiaWorld world;

    @Test
    public void testBasic() throws FileNotFoundException {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Enemy slug = new Slug(position, 0);
        int expected = 10;
        slug.setSupportRange(expected);
        assertEquals(expected, slug.getSupportRange());
        slug.setDetectRange(expected);
        assertEquals(expected, slug.getSupportRange());
        slug.setCritRate(expected);
        assertEquals(expected, slug.getCritRate());
        int expectedAttack = 10;
        int expectedDefence = 10;
        int expectedHealth = 10;
        int expectedCurHealth = 8;
        EntityAttribute attr = new EntityAttribute(expectedAttack, expectedDefence, expectedHealth);
        slug.setAttribute(attr);
        assertEquals(expectedAttack, slug.getAttribute().getAttack().get());
        assertEquals(expectedDefence, slug.getAttribute().getDefence().get());
        assertEquals(expectedHealth, slug.getAttribute().getHealth().get());
        assertEquals(expectedHealth, slug.getAttribute().getCurHealth().get());
        slug.getAttribute().getCurHealth().set(expectedCurHealth);
        assertEquals(expectedCurHealth, slug.getAttribute().getCurHealth().get());
        int expectLv = 1;
        assertEquals(expectLv, slug.getLv());


    }
    
}
