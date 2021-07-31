package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;

public class BuildingTest {
    private LoopManiaWorld world;

    @Test
    public void testBarrack() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        Barrack b = new Barrack(x, y);
        world.getBuildingEntities().add(b);
        Soldier s1 = b.soldierProducer(world);
        Soldier s2 = b.soldierProducer(world);
        Soldier s3 = b.soldierProducer(world);
        Soldier s4 = b.soldierProducer(world);
        Iterator<Soldier> iter = world.getCharacter().getArmy().iterator();
        assertEquals(s1, iter.next());
        assertEquals(s2, iter.next());
        assertEquals(s3, iter.next());
        assertEquals(s4, iter.next());
        assertFalse(iter.hasNext());

        Soldier s5 = b.soldierProducer(world);
        Iterator<Soldier> iter1 = world.getCharacter().getArmy().iterator();
        assertEquals(s2, iter1.next());
        assertEquals(s3, iter1.next());
        assertEquals(s4, iter1.next());
        assertEquals(s5, iter1.next());
        assertFalse(iter1.hasNext());

        world.getCharacter().moveDownPath();
        assertFalse(b.soldierProducer(world) != null);

        String expected = new String("{\"x\":2,\"y\":0,\"type\":\"Barrack\"}");
        assertEquals(expected, b.toJSON().toString());
    }

    @Test
    public void testCampFire() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1() + 1);
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        CampFire b = new CampFire(x, y);
        world.getBuildingEntities().add(b);
        b.specialEffect(world);
        int expectedBuffed = 2;
        int expectedUnbuffed = 1;
        assertEquals(expectedBuffed, world.getCharacter().getCampFireBuff().get());
        world.getCharacter().moveDownPath();
        b.specialEffect(world);
        assertEquals(expectedBuffed, world.getCharacter().getCampFireBuff().get());
        world.getCharacter().moveDownPath();
        world.getCharacter().moveDownPath();
        world.getCharacter().moveDownPath();
        world.getCharacter().moveDownPath();
        b.specialEffect(world);
        assertEquals(expectedUnbuffed, world.getCharacter().getCampFireBuff().get());
        String expected = new String("{\"x\":2,\"y\":1,\"type\":\"Campfire\"}");
        assertEquals(expected, b.toJSON().toString());

    }

    @Test
    public void testTower() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1() + 2);
        
        Tower b = new Tower(x, y);
        world.getBuildingEntities().add(b);
        String expected = new String("{\"x\":2,\"y\":2,\"type\":\"Tower\"}");
        assertEquals(expected, b.toJSON().toString());

        Enemy slug = new Slug(position, 0);
        assertFalse(!b.isInRadius(slug));
        for (int i = 0; i < 14; i++) {
            slug.moveDownPath();
        }
        assertFalse(b.isInRadius(slug));

    }

    @Test
    public void testTrap() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        Enemy slug = new Slug(position, 6);
        Enemy zombie = new Zombie(position, 6);
        world.addEnemy(slug);
        world.addEnemy(zombie);
        Trap b = new Trap(x, y);
        world.getBuildingEntities().add(b);
        int prevHealth = slug.getAttribute().getCurHealth().get();
        int expectedHealth = prevHealth - b.getDamage();
        int prevHealthz = zombie.getAttribute().getCurHealth().get();
        int expectedHealthz = prevHealthz - b.getDamage();
        b.specialEffect(world);
        assertEquals(expectedHealth, slug.getAttribute().getCurHealth().get());
        assertEquals(expectedHealthz, zombie.getAttribute().getCurHealth().get());
        String expected = new String("{\"x\":2,\"y\":0,\"type\":\"Trap\"}");
        assertEquals(expected, b.toJSON().toString());

    }

    @Test
    public void testVampireCastle() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1() + 1);
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        VampireCastle b = new VampireCastle(x, y);
        world.getBuildingEntities().add(b);
        world.getCycle().set(1);
        assertFalse(b.produce(world) != null);
        world.getCycle().set(5);
        Enemy slug = new Slug(position, 1);
        world.addEnemy(slug);
        Enemy slug1 = new Slug(position, 1);
        world.addEnemy(slug1);
        Enemy slug2 = new Slug(position, 1);
        world.addEnemy(slug2);
        Enemy slug3 = new Slug(position, 1);
        world.addEnemy(slug3);
        Enemy slug4 = new Slug(position, 1);
        world.addEnemy(slug4);
        Enemy slug5 = new Slug(position, 1);
        world.addEnemy(slug5);
        Enemy slug6 = new Slug(position, 1);
        world.addEnemy(slug6);
        Enemy slug7 = new Slug(position, 1);
        world.addEnemy(slug7);
        Enemy slug8 = new Slug(position, 1);
        world.addEnemy(slug8);
        Enemy slug9 = new Slug(position, 1);
        world.addEnemy(slug9);
        Enemy slug10 = new Slug(position, 1);
        world.addEnemy(slug10);
        Enemy slug11 = new Slug(position, 1);
        world.addEnemy(slug11);
        Enemy slug12 = new Slug(position, 1);
        world.addEnemy(slug12);
        Enemy slug13 = new Slug(position, 1);
        world.addEnemy(slug13);
        Vampire v = b.produce(world);
        assertEquals(v, world.getEnemies().get(14));
        assertFalse(b.produce(world) != null);
        
        String expected = new String("{\"x\":2,\"y\":1,\"type\":\"VampireCastle\"}");
        assertEquals(expected, b.toJSON().toString());


    }

    @Test
    public void testZombiePit() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1() + 1);
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        ZombiePit b = new ZombiePit(x, y);
        world.getBuildingEntities().add(b);
        world.getCycle().set(1);
        Enemy slug = new Slug(position, 1);
        world.addEnemy(slug);
        Enemy slug1 = new Slug(position, 1);
        world.addEnemy(slug1);
        Enemy slug2 = new Slug(position, 1);
        world.addEnemy(slug2);
        Enemy slug3 = new Slug(position, 1);
        world.addEnemy(slug3);
        Enemy slug4 = new Slug(position, 1);
        world.addEnemy(slug4);
        Enemy slug5 = new Slug(position, 1);
        world.addEnemy(slug5);
        Zombie z = b.produce(world);
        assertEquals(z, world.getEnemies().get(6));
        assertFalse(b.produce(world) != null);
        String expected = new String("{\"x\":2,\"y\":1,\"type\":\"ZombiePit\"}");
        assertEquals(expected, b.toJSON().toString());

    }

    @Test
    public void testVillage() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(2, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(position.getOrderedPath().get(position.getCurrentPositionInPath()).getValue1());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        Village b = new Village(x, y);
        world.getBuildingEntities().add(b);
        world.getCharacter().getAttr().getCurHealth().set(1);
        int originalHealth = world.getCharacter().getAttr().getCurHealth().get();
        int expectedHealth = originalHealth + b.getRegenRate();
        b.specialEffect(world);
        assertEquals(expectedHealth, world.getCharacter().getAttr().getCurHealth().get());
        world.getCharacter().moveDownPath();
        b.specialEffect(world);
        assertEquals(expectedHealth, world.getCharacter().getAttr().getCurHealth().get());

        String expected = new String("{\"x\":2,\"y\":0,\"type\":\"Village\"}");
        assertEquals(expected, b.toJSON().toString());

    }
}
