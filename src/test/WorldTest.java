package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.javatuples.Pair;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.goal.FinalGoal;
import unsw.loopmania.items.*;

public class WorldTest {
    private LoopManiaWorld world;

    @Test
    public void testBasic() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());

        Character ch = new Character(position, world);
        world.setCharacter(ch);
        world.allowAnduril();
        assertFalse(!world.isAnduril());
        world.allowTheOneRing();
        assertFalse(!world.isTheOneRing());
        world.allowTreeStump();
        assertFalse(!world.isTreeStump());
        int mode = 0;
        world.setMode(mode);
        assertEquals(mode, world.getMode());
        assertFalse(world.isConfusing());
        mode = LoopManiaWorld.CONFUSING;
        world.setMode(mode);
        assertFalse(!world.isConfusing());
        String path = new String("foo path");
        world.setPath(path);
        assertEquals(path, world.getPath());
        int expectedPrice = 10;
        world.getDoggiePrice().set(10);
        assertEquals(expectedPrice, world.getDoggiePrice().get());
        assertEquals(14, world.getHeight());
        assertEquals(8, world.getWidth());
        Pair<Integer, Integer> pInPath = new Pair<>(1, 0);
        Pair<Integer, Integer> pOutofPath = new Pair<>(1, 1);
        assertFalse(!world.isInPath(pInPath));
        assertFalse(!world.ifNearPathTile(pOutofPath));
        assertFalse(world.isInPath(pOutofPath));
        assertFalse(!world.atHeroCastle());
        world.getCharacter().moveDownPath();
        assertFalse(world.atHeroCastle());
    }

    @Test
    public void testIntegration() {
        this.world = Helper.createWorld();

    }

    @Test
    public void testLoadCard() {
        this.world = Helper.createWorld();
        assertEquals(0, world.getCardEntities().size());
        VampireCastleCard vc = world.loadVampireCard();
        assertEquals(1, world.getCardEntities().size());
        VillageCard vic = world.loadVillageCard();
        assertEquals(2, world.getCardEntities().size());
        BarrackCard bc = world.loadBarrackCard();
        assertEquals(3, world.getCardEntities().size());
        ZombiePitCard zc = world.loadZombieCard();
        assertEquals(4, world.getCardEntities().size());
        TowerCard tc = world.loadTowerCard();
        assertEquals(5, world.getCardEntities().size());
        TrapCard trc = world.loadTrapCard();
        assertEquals(6, world.getCardEntities().size());
        CampFireCard cfc = world.loadCampFireCard();
        assertEquals(7, world.getCardEntities().size());
        VampireCastleCard vc1 = world.loadVampireCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(vc, world.getCardEntities().get(0));
        world.loadVillageCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(vic, world.getCardEntities().get(0));
        world.loadBarrackCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(bc, world.getCardEntities().get(0));
        world.loadZombieCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(zc, world.getCardEntities().get(0));
        world.loadTowerCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(tc, world.getCardEntities().get(0));
        world.loadTrapCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(trc, world.getCardEntities().get(0));
        world.loadCampFireCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(cfc, world.getCardEntities().get(0));
        world.loadVampireCard();
        assertEquals(8, world.getCardEntities().size());
        assertEquals(vc1, world.getCardEntities().get(0));
    }

    @Test
    public void testCardToBuilding() {
        initializeWorld();
        PathPosition position = new PathPosition(3, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        assertEquals(7, world.getCardEntities().size());
        //vampire castle
        Card c = world.getCardEntities().get(0);
        Building b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 6, 0);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 6, 1);
        assertEquals(1, world.getBuildingEntities().size());
        assertEquals(6, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //village
        c = world.getCardEntities().get(0);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 6, 1);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 6, 0);
        assertEquals(2, world.getBuildingEntities().size());
        assertEquals(5, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //Barrack
        c = world.getCardEntities().get(0);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 6, 1);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 5, 0);
        assertEquals(3, world.getBuildingEntities().size());
        assertEquals(4, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //ZombiePit
        c = world.getCardEntities().get(0);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 1, 0);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 1, 1);
        assertEquals(4, world.getBuildingEntities().size());
        assertEquals(3, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //Tower
        c = world.getCardEntities().get(0);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 3, 1);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 3, 0);
        assertEquals(5, world.getBuildingEntities().size());
        assertEquals(2, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //Trap
        c = world.getCardEntities().get(0);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 2, 2);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 2, 1);
        assertEquals(6, world.getBuildingEntities().size());
        assertEquals(1, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //CampFire
        c = world.getCardEntities().get(0);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 0, 7);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 1, 7);
        assertEquals(7, world.getBuildingEntities().size());
        assertEquals(0, world.getCardEntities().size());
        assertFalse(c.shouldExist().get());
        //spawnenemy
        spawnEnemy();
        assertFalse(world.getEnemies().size() < 1);
        world.getEnemies().clear();
        world.getCycle().set(50);
        spawnElan();
        assertFalse(world.getEnemies().size() < 1);
        world.getEnemies().clear();
        spawnZombie();
        assertFalse(world.getEnemies().size() < 1);
        world.getEnemies().clear();
        spawnDoggie();
        assertFalse(world.getEnemies().size() < 1);
        world.getEnemies().clear();
        spawnVampire();
        assertFalse(world.getEnemies().size() < 1);
        world.getEnemies().clear();
    }

    public void spawnEnemy() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnEnemies();
        }
    }

    public void spawnElan() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnElans();
        }
    }

    public void spawnZombie() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnZombies();
        }
    }

    public void spawnDoggie() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnDoggies();
        }
    }

    public void spawnVampire() {
        for (int i = 0; i < 1000; i++) {
            world.possiblySpawnVampire();
        }
    }

    @Test
    public void testSpawnItem() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        spawnGold();
        assertFalse(world.getSpawnItems().size() < 1);
    }

    public void spawnGold() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnGold();
        }
    }

    @Test
    public void testJSON() {

    }

    public void initializeWorld() {
        this.world = Helper.createWorld();
        assertEquals(0, world.getCardEntities().size());
        world.loadVampireCard();
        assertEquals(1, world.getCardEntities().size());
        world.loadVillageCard();
        assertEquals(2, world.getCardEntities().size());
        world.loadBarrackCard();
        assertEquals(3, world.getCardEntities().size());
        world.loadZombieCard();
        assertEquals(4, world.getCardEntities().size());
        world.loadTowerCard();
        assertEquals(5, world.getCardEntities().size());
        world.loadTrapCard();
        assertEquals(6, world.getCardEntities().size());
        world.loadCampFireCard();
        assertEquals(7, world.getCardEntities().size());
    }

    public void battleBuilder() {
        this.world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(pos, world);
        world.setCharacter(ch);

        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);

        Soldier so1 = new Soldier(x, y, ch);
        // so1.setAttr(new EntityAttribute(0, 0, 20000));
        ch.addSoldier(so1);
        Soldier so2 = new Soldier(x, y, ch);
        ch.addSoldier(so2);
        Soldier so3 = new Soldier(x, y, ch);
        ch.addSoldier(so3);

        Enemy slug = new Slug(pos, 1);
        world.getEnemies().add(slug);
        Enemy zombie = new Zombie(pos, 2);
        world.getEnemies().add(zombie);
        Enemy vampire = new Vampire(pos, 2);
        world.getEnemies().add(vampire);
        Enemy doggie = new Doggie(pos, 1);
        world.getEnemies().add(doggie);
        Enemy elan = new ElanMuske(pos, 1);
        world.getEnemies().add(elan);
    }

    @Test 
    public void testBattleWithSword() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Sword sword = new Sword(x, y);
        world.getEquip().equipWeapon(sword);
        List<Enemy> defeated = new ArrayList<>();
        world.battle(world.getEnemies(), defeated, world.getCharacter());
        assertTrue(!world.getCharacter().shouldExist().get());
        assertTrue(world.getEnemies().size() > defeated.size());
    }

    @Test
    public void testBattleWithStake() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Stake stake= new Stake(x, y);
        world.getEquip().equipWeapon(stake);
        List<Enemy> defeated = new ArrayList<>();
        world.battle(world.getEnemies(), defeated, world.getCharacter());
        assertTrue(!world.getCharacter().shouldExist().get());
        assertTrue(world.getEnemies().size() > defeated.size());
    }

    @Test
    public void testBattleWithStaff() {
        battleBuilder();
        world.getCharacter().getArmy().get(0).setAttr(new EntityAttribute(0, 0, 50000));
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Staff staff = new Staff(x, y);
        world.getEquip().equipWeapon(staff);
        List<Enemy> defeated = new ArrayList<>();
        List<Enemy> all = new ArrayList<>(world.getEnemies());
        all.remove(1);
        world.battle(all, defeated, world.getCharacter());
        assertTrue(!world.getCharacter().shouldExist().get() || defeated.size() == 5);
    }

    @Test
    public void testBattleWithAnduril() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Anduril anduril = new Anduril(x, y, false);
        world.getEquip().equipWeapon(anduril);
        List<Enemy> defeated = new ArrayList<>();
        world.battle(world.getEnemies(), defeated, world.getCharacter());
        assertTrue(!world.getCharacter().shouldExist().get() || defeated.size() == 5);
    }

    @Test
    public void testBattleWithOutfit() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Anduril anduril = new Anduril(x, y, false);
        Helmet helmet = new Helmet(x,y);
        Shield shield = new Shield(x,y);
        Armour armour = new Armour(x,y);

        world.getEquip().equipArmour(armour);
        world.getEquip().equipShield(shield);
        world.getEquip().equipHelmet(helmet);
        world.getEquip().equipWeapon(anduril);
        List<Enemy> defeated = new ArrayList<>();
        world.battle(world.getEnemies(), defeated, world.getCharacter());
        assertTrue(!world.getCharacter().shouldExist().get() || defeated.size() == 5);
    }

    @Test
    public void testBattleWithTreeStump() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Anduril anduril = new Anduril(x, y, false);
        Helmet helmet = new Helmet(x,y);
        Shield treestump = new TreeStump(x,y, false);
        Armour armour = new Armour(x,y);

        world.getEquip().equipArmour(armour);
        world.getEquip().equipShield(treestump);
        world.getEquip().equipHelmet(helmet);
        world.getEquip().equipWeapon(anduril);
        List<Enemy> defeated = new ArrayList<>();
        world.battle(world.getEnemies(), defeated, world.getCharacter());
        assertTrue(!world.getCharacter().shouldExist().get() || defeated.size() == 5);
    }

    @Test
    public void testRunBattle() {

    }


}
