package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;

public class EnemyTest {

    private LoopManiaWorld world;

    @Test
    public void testBasic() throws FileNotFoundException {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Enemy slug = new Slug(position, 0);
        Enemy doggie = new Doggie(position, 10);
        assertEquals(40, doggie.getCritRate());
        Enemy zombie = new Zombie(position, 10);
        assertEquals(60, zombie.getCritRate());
        Vampire vampire = new Vampire(position, 10);
        assertEquals(60, vampire.getCritRate());
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
        int expectedRadi = 1;
        vampire.setRadusCampfire(1);
        assertEquals(expectedRadi, vampire.getRadusCampfire());


    }

    @Test
    public void testBasicMove() throws FileNotFoundException {
        this.world = Helper.createWorld();
        int prevPosition = 1;
        PathPosition position = new PathPosition(prevPosition + 1, world.getOrderedPath());
        Enemy slug = new Slug(position, 0);
        world.addEnemy(slug);
        slug.move(world);
        assertFalse(Math.abs(world.getEnemies().get(0).getPosition().getCurrentPositionInPath() - prevPosition) > 3);
        Enemy zombie = new Zombie(position, 0);
        world.addEnemy(zombie);
        zombie.move(world);
        assertFalse(Math.abs(world.getEnemies().get(1).getPosition().getCurrentPositionInPath() - prevPosition) > 3);
        Enemy vampire = new Vampire(position, 0);
        world.addEnemy(vampire);
        vampire.move(world);
        assertFalse((world.getEnemies().get(2).getPosition().getCurrentPositionInPath() - prevPosition) > 3);
    }
    
    @Test
    public void testVampireSpecialMove() throws FileNotFoundException {
        this.world = Helper.createWorld();
        int prevPosition = 1;
        PathPosition position = new PathPosition(prevPosition + 1, world.getOrderedPath());
        PathPosition positionFront = new PathPosition(prevPosition + 2, world.getOrderedPath());
        PathPosition positionFront2 = new PathPosition(prevPosition, world.getOrderedPath());

        PathPosition positionBehind = new PathPosition(prevPosition - 1, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(positionFront.getOrderedPath().get(positionFront.getCurrentPositionInPath()).getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(positionFront.getOrderedPath().get(positionFront.getCurrentPositionInPath()).getValue1() + 1);
        Building cf = new CampFire(x, y);
        world.getBuildingEntities().add(cf);
        Enemy vampire = new Vampire(position, 0);
        world.addEnemy(vampire);
        vampire.move(world);
        assertFalse((world.getEnemies().get(0).getPosition().getCurrentPositionInPath() - prevPosition) != 0);
        //world.getBuildingEntities().remove(cf);
        x.set(positionBehind.getOrderedPath().get(positionBehind.getCurrentPositionInPath()).getValue0());
        y.set(positionBehind.getOrderedPath().get(positionBehind.getCurrentPositionInPath()).getValue1() + 1);
        Building cf1 = new CampFire(x, y);
        world.getBuildingEntities().add(cf1);
        vampire.move(world);
        assertFalse((world.getEnemies().get(0).getPosition().getCurrentPositionInPath() - prevPosition) != 1);
        world.getBuildingEntities().remove(cf1);
        x.set(positionFront2.getOrderedPath().get(positionFront2.getCurrentPositionInPath()).getValue0());
        y.set(positionFront2.getOrderedPath().get(positionFront2.getCurrentPositionInPath()).getValue1() + 1);
        Building cf2 = new CampFire(x, y);
        world.getBuildingEntities().add(cf2);
        vampire.move(world);
        assertFalse((world.getEnemies().get(0).getPosition().getCurrentPositionInPath() - prevPosition) != 0);
    }

    @Test
    public void testToJSON() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Enemy slug = new Slug(position, 0);
        String expectedSlug = new String("{\"lv\":1,\"position\":1,\"type\":\"Slug\"}");
        assertEquals(expectedSlug, slug.toJSON().toString());
        Enemy zombie = new Zombie(position, 0);
        String expectedZombie = new String("{\"lv\":1,\"position\":1,\"type\":\"Zombie\"}");
        assertEquals(expectedZombie, zombie.toJSON().toString());
        Enemy vampire = new Vampire(position, 0);
        String expectedVampire = new String("{\"lv\":1,\"position\":1,\"type\":\"Vampire\"}");
        assertEquals(expectedVampire, vampire.toJSON().toString());
        Enemy doggie = new Doggie(position, 0);
        String expectedDoggie = new String("{\"lv\":1,\"position\":1,\"type\":\"Doggie\"}");
        assertEquals(expectedDoggie, doggie.toJSON().toString());
        Enemy elan = new ElanMuske(position, 0);
        String expectedElan = new String("{\"lv\":1,\"position\":1,\"type\":\"Elan\"}");
        assertEquals(expectedElan, elan.toJSON().toString());
    }

    @Test
    public void testElanEffect() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        ElanMuske elan = new ElanMuske(position, 1);
        world.getDoggiePrice().set(4000);
        elan.increaseDoggiePrice(world);
        assertEquals(12000, world.getDoggiePrice().get());
        elan.decreaseDoggiePrice(world);
        assertEquals(3000, world.getDoggiePrice().get());
    }
}
