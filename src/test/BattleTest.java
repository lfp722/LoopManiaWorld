package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;

public class BattleTest {

    private LoopManiaWorld world;

    @Test
    public void testCharacterBattleBasic() {
        this.world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(pos, world);
        world.setCharacter(ch);
        
        Enemy slug = new Slug(pos, 0);
        assertFalse(!slug.shouldExist().get());
        ch.attack(slug);
        assertEquals(0, slug.getAttribute().getCurHealth().get());
        assertFalse(slug.shouldExist().get());

        slug.attack(ch);
        assertEquals(33, ch.getAttr().getCurHealth().get());
        assertFalse(!ch.shouldExist().get());

        Enemy elan = new ElanMuske(pos, 4);
        elan.attack(ch);
        assertEquals(0, ch.getAttr().getCurHealth().get());
        assertFalse(ch.shouldExist().get());

        ch.heal(35);
        ch.shouldExist().set(true);

        Soldier sod = new Soldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), ch);
        assertEquals(ch.getAttr().copy(5).getAttack().get(), sod.getAttr().getAttack().get());
        assertEquals(ch.getAttr().copy(5).getDefence().get(), sod.getAttr().getDefence().get());
        assertEquals(ch.getAttr().copy(5).getHealth().get(), sod.getAttr().getHealth().get());

        sod.setAttr(new EntityAttribute(10,10,10));
        assertEquals(10, sod.getAttr().getAttack().get());
        assertEquals(10, sod.getAttr().getDefence().get());
        assertEquals(10, sod.getAttr().getHealth().get());

        sod.attack(elan);
        assertEquals(140 ,elan.getAttribute().getCurHealth().get());

        sod.underAttack(10);
        assertEquals(0, sod.getAttr().getCurHealth().get());
        assertFalse(sod.shouldExist().get());
    }




    
}
