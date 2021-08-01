package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.javatuples.Pair;
import org.json.JSONObject;
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
        List<Item> list = world.getBoughtItem();
        assertFalse(!list.isEmpty());
        Equipped expected = new Equipped(world);
        world.setEquippedItems(expected);
        assertEquals(expected, world.getEquippedItems());
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
        Building b = world.convertCardToBuildingByCoordinates(c.simpleX().get(), c.simpleY().get(), 6, 0);
        assertFalse(b != null);
        b = world.convertCardToBuildingByCoordinates(c.x().get(), c.y().get(), 6, 1);
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
        world.pickUp();
        assertEquals(0, world.getInventory().size());
        int x = world.getSpawnItems().get(0).getX();
        int y = world.getSpawnItems().get(0).getY();
        ch.getPosition().getX().set(x);
        ch.getPosition().getY().set(y);
        world.pickUp();
        assertFalse(world.getCharacter().getGold() < 1);
        world.getSpawnItems().clear();
        spawnPotion();
        assertFalse(world.getSpawnItems().size() < 1);
        world.pickUp();
        assertEquals(0, world.getInventory().size());
        x = world.getSpawnItems().get(0).getX();
        y = world.getSpawnItems().get(0).getY();
        ch.getPosition().getX().set(x);
        ch.getPosition().getY().set(y);
        world.pickUp();
        assertFalse(world.getInventory().size() < 1);
        world.getSpawnItems().clear();
    }

    public void spawnGold() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnGold();
        }
    }

    public void spawnPotion() {
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnPotion();
        }
    }

    @Test
    public void testInventory() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        assertEquals(0, world.getInventory().size());
        Potion p = world.addPotion();
        assertEquals(1, world.getInventory().size());
        assertEquals(p, world.getInventory().get(0));
        Anduril an = world.addUnequippedAnduril();
        assertEquals(2, world.getInventory().size());
        assertEquals(an, world.getInventory().get(1));
        Armour ar = world.addUnequippedArmour();
        assertEquals(3, world.getInventory().size());
        assertEquals(ar, world.getInventory().get(2));
        DoggieCoin d = world.addDoggie();
        assertEquals(4, world.getInventory().size());
        assertEquals(d, world.getInventory().get(3));
        Helmet h = world.addUnequippedHelmet();
        assertEquals(5, world.getInventory().size());
        assertEquals(h, world.getInventory().get(4));
        Shield sh = world.addUnequippedShield();
        assertEquals(6, world.getInventory().size());
        assertEquals(sh, world.getInventory().get(5));
        Staff staff = world.addUnequippedStaff();
        assertEquals(7, world.getInventory().size());
        assertEquals(staff, world.getInventory().get(6));
        Stake stake = world.addUnequippedStake();
        assertEquals(8, world.getInventory().size());
        assertEquals(stake, world.getInventory().get(7));
        Sword sword = world.addUnequippedSword();
        assertEquals(9, world.getInventory().size());
        assertEquals(sword, world.getInventory().get(8));
        TreeStump ts = world.addUnequippedTreeStump();
        assertEquals(10, world.getInventory().size());
        assertEquals(ts, world.getInventory().get(9));
        for (int i = 0; i < 6; i++) {
            DoggieCoin dg = world.addDoggie();
            assertEquals(dg, world.getInventory().get(10 + i));
        }
        Item it = world.getInventory().get(1);
        world.addPotion();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedArmour();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedAnduril();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedHelmet();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedShield();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedStaff();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedStake();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedSword();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addDoggie();
        assertEquals(it, world.getInventory().get(0));
        it = world.getInventory().get(1);
        world.addUnequippedTreeStump();
        assertEquals(it, world.getInventory().get(0));
        world.getInventory().clear();
    }

    @Test
    public void testEquip() {
        this.world = Helper.createWorld();
        PathPosition position  = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        TheOneRing ring = world.addEquippedRing();
        assertEquals(ring, world.getEquip().getRing());
        ring = world.addEquippedRing();
        assertEquals(ring, world.getEquippedItems().getRing());   
        Potion p = world.addPotion();
        assertEquals(1, world.getInventory().size());
        assertEquals(p, world.getInventory().get(0));
        Anduril an = world.addUnequippedAnduril();
        assertEquals(2, world.getInventory().size());
        assertEquals(an, world.getInventory().get(1));
        Armour ar = world.addUnequippedArmour();
        assertEquals(3, world.getInventory().size());
        assertEquals(ar, world.getInventory().get(2));
        DoggieCoin d = world.addDoggie();
        assertEquals(4, world.getInventory().size());
        assertEquals(d, world.getInventory().get(3));
        Helmet h = world.addUnequippedHelmet();
        assertEquals(5, world.getInventory().size());
        assertEquals(h, world.getInventory().get(4));
        Shield sh = world.addUnequippedShield();
        assertEquals(6, world.getInventory().size());
        assertEquals(sh, world.getInventory().get(5));
        Staff staff = world.addUnequippedStaff();
        assertEquals(7, world.getInventory().size());
        assertEquals(staff, world.getInventory().get(6));
        Stake stake = world.addUnequippedStake();
        assertEquals(8, world.getInventory().size());
        assertEquals(stake, world.getInventory().get(7));
        Sword sword = world.addUnequippedSword();
        assertEquals(9, world.getInventory().size());
        assertEquals(sword, world.getInventory().get(8));
        TreeStump ts = world.addUnequippedTreeStump();
        assertEquals(10, world.getInventory().size());
        assertEquals(ts, world.getInventory().get(9));
        Sword s = world.addUnequippedSword();
        Shield shield = world.addUnequippedShield();
        Armour armour = world.addUnequippedArmour();
        Helmet helmet = world.addUnequippedHelmet();
        assertFalse(world.covertEquippedToEquipped(0, 0) != null);
        world.covertEquippedToEquipped(an.getX(), an.getY());
        assertFalse(!(world.getEquip().getWeapon() instanceof Anduril));
        world.covertEquippedToEquipped(sword.getX(), sword.getY());
        assertFalse(!(world.getEquip().getWeapon() instanceof Sword));
        world.covertEquippedToEquipped(stake.getX(), stake.getY());
        assertFalse(!(world.getEquip().getWeapon() instanceof Stake));
        world.covertEquippedToEquipped(staff.getX(), staff.getY());
        assertFalse(!(world.getEquip().getWeapon() instanceof Staff));
        world.covertEquippedToEquipped(h.getX(), h.getY());
        assertFalse(!(world.getEquip().getHelmet() instanceof Helmet));
        world.covertEquippedToEquipped(helmet.getX(), helmet.getY());
        assertFalse(!(world.getEquip().getHelmet() instanceof Helmet));
        world.covertEquippedToEquipped(ar.getX(), ar.getY());
        assertFalse(!(world.getEquip().getArmour() instanceof Armour));
        world.covertEquippedToEquipped(armour.getX(), armour.getY());
        assertFalse(!(world.getEquip().getArmour() instanceof Armour));
        world.covertEquippedToEquipped(sh.getX(), sh.getY());
        assertFalse(!(world.getEquip().getShield() instanceof Shield));
        world.covertEquippedToEquipped(ts.getX(), ts.getY());
        assertFalse(!(world.getEquip().getShield() instanceof TreeStump));
        world.covertEquippedToEquipped(shield.getX(), shield.getY());
        assertFalse(!(world.getEquip().getShield() instanceof Shield));
    }

    @Test
    public void testJSON() {
        battleBuilder();
        Potion p = world.addPotion();
        Anduril an = world.addUnequippedAnduril();
        Armour ar = world.addUnequippedArmour();
        DoggieCoin d = world.addDoggie();
        Helmet h = world.addUnequippedHelmet();
        Shield sh = world.addUnequippedShield();
        Staff staff = world.addUnequippedStaff();
        Stake stake = world.addUnequippedStake();
        Sword sword = world.addUnequippedSword();
        TreeStump ts = world.addUnequippedTreeStump();
        world.covertEquippedToEquipped(ar.getX(), ar.getY());
        world.getEquip().getArmour().levelUp();
        world.covertEquippedToEquipped(sword.getX(), sword.getY());
        world.getEquip().getWeapon().levelUp();
        world.covertEquippedToEquipped(h.getX(), h.getY());
        world.getEquip().getHelmet().levelUp();
        world.covertEquippedToEquipped(sh.getX(), sh.getY());
        world.getEquip().getHelmet().levelUp();
        
        world.getDoggiePrice().set(10);
        world.getCycle().set(10);
        spawnPotion();
        world.getSpawnItems().remove(world.getSpawnItems().size() - 1);
        spawnGold();
        JSONObject json = world.toJSON();
        LoopManiaWorld newWorld = Helper.createWorld();
        PathPosition position = new PathPosition(0, newWorld.getOrderedPath());
        Character ch = new Character(position, newWorld);

        newWorld.setCharacter(ch);
        newWorld.readFromJSON(json);
        assertEquals(world.toJSON().toString(), newWorld.toJSON().toString());
        
        world.covertEquippedToEquipped(staff.getX(), staff.getY());
        world.getEquip().getWeapon().levelUp();
        world.covertEquippedToEquipped(ts.getX(), ts.getY());
        world.getEquip().getShield().levelUp();
        json = world.toJSON();
        newWorld = Helper.createWorld();
        newWorld.setCharacter(ch);
        newWorld.readFromJSON(json);
        assertEquals(world.toJSON().toString(), newWorld.toJSON().toString());
        
        world.covertEquippedToEquipped(stake.getX(), stake.getY());
        world.getEquip().getWeapon().levelUp();
        world.covertEquippedToEquipped(ts.getX(), ts.getY());
        json = world.toJSON();
        newWorld = Helper.createWorld();
        newWorld.setCharacter(ch);
        newWorld.readFromJSON(json);
        assertEquals(world.toJSON().toString(), newWorld.toJSON().toString());

        world.covertEquippedToEquipped(an.getX(), an.getY());
        world.getEquip().getWeapon().levelUp();
        world.covertEquippedToEquipped(ts.getX(), ts.getY());
        json = world.toJSON();
        newWorld = Helper.createWorld();
        newWorld.setCharacter(ch);
        newWorld.readFromJSON(json);
        assertEquals(world.toJSON().toString(), newWorld.toJSON().toString());
        
        world.addUnequippedSword();
        world.addUnequippedHelmet();
        world.addUnequippedArmour();
        world.addUnequippedShield();
        BarrackCard b = world.loadBarrackCard();
        CampFireCard c = world.loadCampFireCard();
        ZombiePitCard z = world.loadZombieCard();
        VampireCastleCard vc = world.loadVampireCard();
        VillageCard vi = world.loadVillageCard();
        TowerCard bc = world.loadTowerCard();
        TrapCard tc = world.loadTrapCard();
        
        world.getHeroCastle();
        world.convertCardToBuildingByCoordinates(bc.getX(), bc.getY(), 1, 1);
        world.convertCardToBuildingByCoordinates(b.getX(), b.getY(), 0, 1);
        world.convertCardToBuildingByCoordinates(z.getX(), z.getY(), 1, 2);
        world.convertCardToBuildingByCoordinates(vc.getX(), vc.getY(), 1, 3);
        world.convertCardToBuildingByCoordinates(vi.getX(), vi.getY(), 0, 2);
        world.convertCardToBuildingByCoordinates(c.getX(), c.getY(), 1, 4);
        world.convertCardToBuildingByCoordinates(tc.getX(), tc.getY(), 2, 1);
        world.loadBarrackCard();
        world.loadCampFireCard();
        world.loadZombieCard();
        world.loadVampireCard();
        world.loadVillageCard();
        world.loadTowerCard();
        world.loadTrapCard();
        json = world.toJSON();
        newWorld = Helper.createWorld();
        newWorld.getHeroCastle();
        newWorld.setCharacter(ch);
        newWorld.readFromJSON(json);
        //assertEquals(world.getBuildingEntities().get(0).toJSON().toString(), newWorld.getBuildingEntities().get(0).toJSON().toString());
        assertEquals(world.toJSON().toString(), newWorld.toJSON().toString());
  
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
        assertTrue(!world.getCharacter().shouldExist().get() || (defeated.size() >= 5 && defeated.size() <= 8));
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
        assertTrue(!world.getCharacter().shouldExist().get() || (defeated.size() >= 5 && defeated.size() <= 8));
    }

    @Test
    public void testBattleWithStaff() {
        battleBuilder();
        world.getCharacter().getArmy().get(0).setAttr(new EntityAttribute(0, 0, 50000));
        world.getCharacter().getArmy().remove(2);
        world.getCharacter().getArmy().remove(1);
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Staff staff = new Staff(x, y);
        world.getEquip().equipWeapon(staff);
        List<Enemy> defeated = new ArrayList<>();

        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        world.getEnemies().clear();
        for (int i = 0; i < 20; i++) {
            Enemy slug = new Slug(pos, 5);
            world.getEnemies().add(slug);
        }

        //System.out.println(defeated.size());
        List<Enemy> all = new ArrayList<>(world.getEnemies());
        world.battle(all, defeated, world.getCharacter());


        assertTrue(!world.getCharacter().shouldExist().get() || defeated.size() == 20);
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
        assertTrue(!world.getCharacter().shouldExist().get() || (defeated.size() >= 5 && defeated.size() <= 8));
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
        assertTrue(!world.getCharacter().shouldExist().get() || (defeated.size() >= 5 && defeated.size() <= 8));
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
        System.out.println(world.getCharacter().shouldExist().get());
        System.out.println(defeated.size());
        
        assertTrue(!world.getCharacter().shouldExist().get() || (defeated.size() >= 5 && defeated.size() <= 8));
    }

    @Test
    public void testRunBattle() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Tower tower = new Tower(x, y);
        world.getBuildingEntities().add(tower);
        List<Enemy> defeated = world.runBattles();
        assertTrue(!world.getCharacter().shouldExist().get() || (defeated.size() >= 5 && defeated.size() <= 8));
    }

    @Test
    public void testRestart() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Building newB = new Tower(x, y);
        world.getBuildingEntities().add(newB);
        Card newC = new ZombiePitCard(x, y);
        world.getCardEntities().add(newC);
        for (int i = 0; i < 100; i++) {
            world.possiblySpawnPotion();
        }
        for (int i = 0; i < 120; i++) {
            world.runTickMoves();
            if (!world.pickUp().isEmpty()) {
                break;
            }
        }
        world.restart();
        assertTrue(world.getEnemies().isEmpty());
        assertTrue(world.getCharacter().getArmy().isEmpty());
        assertTrue(world.getBuildingEntities().isEmpty());
        assertTrue(world.getSpawnItems().isEmpty());
        assertTrue(world.getInventory().isEmpty());
        assertTrue(world.getCardEntities().isEmpty());
    }

    @Test
    public void testAcademy() {
        world = Helper.createWorld();
        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(pos, world);
        world.setCharacter(ch);
        world.getAcademy();
        for (int i = 0; i < world.getOrderedPath().size()/2; i++) {
            world.runTickMoves();
        }

        assertTrue(world.checkAcademy());
        world.runTickMoves();

        assertFalse(world.checkAcademy());
    }


    @Test
    public void testConsumePotion() {
        battleBuilder();
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        Potion p0 = new Potion(x,y);
        world.getInventory().add(p0);
        Potion p1 = new Potion(x,y);
        world.getInventory().add(p1);
        Potion p2 = new Potion(x,y);
        world.getInventory().add(p2);
        Potion p3 = new Potion(x,y);
        world.getInventory().add(p3);
        Potion p4 = new Potion(x,y);
        world.getInventory().add(p4);
        Potion p5 = new Potion(x,y);
        world.getInventory().add(p5);
        Potion p6 = new Potion(x,y);
        world.getInventory().add(p6);

        assertTrue(world.getInventory().size() == 7);
        for (int i = 0; i < 7; i++) {
            world.consumePotion();
        }

        assertTrue(world.getInventory().isEmpty());

    }


}
