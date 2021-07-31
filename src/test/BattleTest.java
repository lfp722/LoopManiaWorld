package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.transformation.SortedList;
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

    @Test
    public void testZombieCritical() {
        this.world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(pos, world);
        world.setCharacter(ch);

        Soldier sod = new Soldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), ch);

        ch.addSoldier(sod);

        assertEquals(1, ch.getArmy().size());

        Enemy slug = new Slug(pos, 5);

        Enemy zombie = new Zombie(pos, 0);

        world.getEnemies().add(zombie);

        assertEquals(1, world.getEnemies().size());

        boolean criA = false;
        boolean norA = false;

        for (int i = 0; i < 100; i++) {
            ch.heal(20);
            zombie.attack(ch);
            if (ch.getAttr().getHealth().get() - ch.getAttr().getCurHealth().get() == 11) {
                norA = true;
            }
            else if (ch.getAttr().getHealth().get() - ch.getAttr().getCurHealth().get() == 16) {
                criA = true;
            }
        }

        assertTrue(norA);
        assertTrue(criA); 

        norA = false;
        criA = false;
        
        for (int i = 0; i < 100; i++) {
            slug.getAttribute().getCurHealth().set(slug.getAttribute().getHealth().get());
            zombie.attack(slug);

            if (slug.getAttribute().getHealth().get() - slug.getAttribute().getCurHealth().get() == 11) {
                norA = true;
            }
            else if (slug.getAttribute().getHealth().get() - slug.getAttribute().getCurHealth().get() == 13) {
                criA = true;
            }

        }

        assertTrue(norA);
        assertTrue(criA); 



        norA = false;
        criA = false;
        
        for (int i = 0; i < 100; i++) {
            sod.getAttr().getCurHealth().set(sod.getAttr().getHealth().get());
            zombie.attack(sod, world);

            if (sod.getAttr().getCurHealth().get() == 0) {
                norA = true;
            }
            else if (world.getEnemies().size() == 2 && ch.getArmy().size() == 0) {
                criA = true;
            }

        }
        
        assertTrue(norA);
        assertTrue(criA); 
    }

    @Test
    public void testVampireCritical() {
        this.world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(pos, world);
        world.setCharacter(ch);

        Soldier sod = new Soldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), ch);

        ch.addSoldier(sod);

        assertEquals(1, ch.getArmy().size());

        Enemy slug = new Slug(pos, 5);

        Enemy vampire = new Vampire(pos, 0);

        world.getEnemies().add(vampire);

        assertEquals(1, world.getEnemies().size());

        boolean criA = false;
        boolean norA = false;

        for (int i = 0; i < 100; i++) {
            ch.heal(20);
            vampire.attack(ch);
            if (ch.getAttr().getHealth().get() - ch.getAttr().getCurHealth().get() == 6) {
                norA = true;
            }
            else if (ch.getAttr().getHealth().get() - ch.getAttr().getCurHealth().get() == 12) {
                criA = true;
            }
        }

        assertTrue(norA);
        assertTrue(criA);

        norA = false;
        criA = false;
        
        sod.setAttr(new EntityAttribute(10,10,10));
        System.out.println(sod.toJSON().toString());
        for (int i = 0; i < 100; i++) {
            sod.getAttr().getCurHealth().set(sod.getAttr().getHealth().get());
            vampire.attack(sod, world);

            if (sod.getAttr().getHealth().get() - sod.getAttr().getCurHealth().get() == 6) {
                norA = true;
            }

            else if (sod.getAttr().getHealth().get() - sod.getAttr().getCurHealth().get() == 9) {
                criA = true;
            }

        }
        
        assertTrue(norA);
        assertTrue(criA); 

        norA = false;
        criA = false;
        for (int i = 0; i < 100; i++) {
            slug.getAttribute().getCurHealth().set(slug.getAttribute().getHealth().get());
            vampire.attack(slug);

            if (slug.getAttribute().getHealth().get() - slug.getAttribute().getCurHealth().get() == 6) {
                norA = true;
            }

            else if (slug.getAttribute().getHealth().get() - slug.getAttribute().getCurHealth().get() == 9) {
                criA = true;
            }

        }
        
        assertTrue(norA);
        assertTrue(criA); 

    } 

    @Test 
    public void testDoggieAttack() {
        this.world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(pos, world);
        world.setCharacter(ch);

        Soldier sod = new Soldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), ch);

        ch.addSoldier(sod);

        assertEquals(1, ch.getArmy().size());

        Enemy slug = new Slug(pos, 5);

        Enemy doggie = new Doggie(pos, 0);

        world.getEnemies().add(doggie);

        assertEquals(1, world.getEnemies().size());

        boolean criA = false;
        boolean norA = false;

        for (int i = 0; i < 100; i++) {
            ch.heal(20);
            doggie.attack(ch);
            if (ch.getAttr().getHealth().get() - ch.getAttr().getCurHealth().get() == 2) {
                norA = true;
            }
            if (ch.isStunned()) {
                criA = true;
            }
        }

        assertTrue(norA);
        assertTrue(criA);

    }


    @Test 
    public void testElan() {
        this.world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        List<Enemy> enimies = new ArrayList<>();
        ElanMuske elan = new ElanMuske(pos, 0);
        elan.getAttribute().getCurHealth().set(1);
        enimies.add(elan);
        Enemy slug = new Slug(pos, 1);
        slug.getAttribute().getCurHealth().set(1);
        enimies.add(slug);
        Enemy zombie = new Zombie(pos, 2);
        zombie.getAttribute().getCurHealth().set(1);
        enimies.add(zombie);
        Enemy vampire = new Vampire(pos, 2);
        vampire.getAttribute().getCurHealth().set(1);
        enimies.add(vampire);
        Enemy doggie = new Doggie(pos, 1);
        doggie.getAttribute().getCurHealth().set(1);
        enimies.add(doggie);
        Enemy elan_copy = new ElanMuske(pos, 1);
        elan_copy.getAttribute().getCurHealth().set(1);
        enimies.add(elan_copy);

        for (int i = 0; i < 100; i++) {
            elan.healEnemy(enimies);
        }

        assertEquals(1, elan.getAttribute().getCurHealth().get());
        assertEquals(slug.getAttribute().getHealth().get(), slug.getAttribute().getCurHealth().get());
        assertEquals(zombie.getAttribute().getHealth().get(), zombie.getAttribute().getCurHealth().get());
        assertEquals(vampire.getAttribute().getHealth().get(), vampire.getAttribute().getCurHealth().get());
        assertEquals(doggie.getAttribute().getHealth().get(), doggie.getAttribute().getCurHealth().get());
        assertEquals(elan_copy.getAttribute().getHealth().get(), elan_copy.getAttribute().getCurHealth().get());
    }



    
}
