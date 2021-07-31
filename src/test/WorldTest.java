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
        //PathPosition position1 = new PathPosition(1, world.getOrderedPath());

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
    public void testJSON() {
        
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

        PathPosition pos = new PathPosition(0, world.getOrderedPath());
        Slug s0 = new Slug(pos, 0);
        all.add(s0);
        Slug s1 = new Slug(pos, 0);
        all.add(s1);
        Slug s2 = new Slug(pos, 0);
        all.add(s2);
        Slug s3 = new Slug(pos, 0);
        all.add(s3);
        Slug s4 = new Slug(pos, 0);
        all.add(s4);
        Slug s5 = new Slug(pos, 0);
        all.add(s5);
        Slug s6 = new Slug(pos, 0);
        all.add(s6);
        Slug s7 = new Slug(pos, 0);
        all.add(s7);
        Slug s8 = new Slug(pos, 0);
        all.add(s8);
        Slug s9 = new Slug(pos, 0);
        all.add(s9);
        Slug s10 = new Slug(pos, 0);
        all.add(s10);
        all.remove(1);

        //System.out.println(defeated.size());

        world.battle(all, defeated, world.getCharacter());


        assertTrue(!world.getCharacter().shouldExist().get() || defeated.size() == 15);
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
