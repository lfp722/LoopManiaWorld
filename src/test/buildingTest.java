package test;


import test.Helper;
import unsw.loopmania.*;
import unsw.loopmania.Character;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.Painter;

import org.junit.Test;
import org.junit.jupiter.api.DisplayNameGenerator.Simple;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;

public class buildingTest {
    private static final int CURRENT = 0;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;
    private static final int MAP1 = 1;

    //add buiding
    @Test
    public void test_building_create() {    
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(MAP1);

        Building barrack = helper.createBuildingSetup("Barrack", 30, world, CURRENT);
        assertEquals(barrack.getX(), 3);
        assertEquals(barrack.getY(), 3);
        assertEquals(world.getBuildingEntities().size(), 0);

        Building vampireCastle = helper.createBuildingSetup("VampireCastle", 30, world, UP);
        assertEquals(vampireCastle.getX(), 3);
        assertEquals(vampireCastle.getY(), 2);
        assertEquals(world.getBuildingEntities().size(), 0);
        
        Building zombiePit = helper.createBuildingSetup("ZombiePit", 30, world, DOWN);
        assertEquals(zombiePit.getX(), 3);
        assertEquals(zombiePit.getY(), 4);
        assertEquals(world.getBuildingEntities().size(), 0);

        Building village = helper.createBuildingSetup("Village", 30, world, RIGHT);
        assertEquals(4, village.getX());
        assertEquals(3, village.getY());
        assertEquals(world.getBuildingEntities().size(), 0);

        Building trap = helper.createBuildingSetup("Trap", 30, world, LEFT);
        assertEquals(trap.getX(), 2);
        assertEquals(trap.getY(), 3);
        assertEquals(world.getBuildingEntities().size(), 0);

        Building campfire= helper.createBuildingSetup("Campfire", 30, world, LEFT);
        assertEquals(campfire.getX(), 2);
        assertEquals(campfire.getY(), 3);
        assertEquals(world.getBuildingEntities().size(), 0);

    }


    //Barrack produce soldier
    @Test
    public void test_barrack_produce() {
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(MAP1);
        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();
        PathPosition pathposition = new PathPosition(1, orderedPath);
        SimpleIntegerProperty x = pathposition.getX();
        SimpleIntegerProperty y = pathposition.getY();

        Barrack barrack = new Barrack(x, y);

        Character character = helper.testCharacterSetup(1, MAP1);
        barrack.soldierProducer(world);
        assertEquals(character.getArmy().size(), 1);

    }

    @Test
    public void test_campfire() {
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(MAP1);
        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();
        PathPosition pathposition = new PathPosition(1, orderedPath);
        SimpleIntegerProperty x = pathposition.getX();
        SimpleIntegerProperty y = pathposition.getY();
        Character character = helper.testCharacterSetup(1, MAP1);
        EntityAttribute chAttr = character.getAttr();
        int attack = chAttr.getAttack().getValue();
        assertEquals(attack, 5);        
        CampFire campfire = new CampFire(x, y);
        assertEquals(campfire.isInRadius(character), true);
    }

    //
    @Test
    public void test_tower() {
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(MAP1);
        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();
        PathPosition pathposition = new PathPosition(1, orderedPath);
        SimpleIntegerProperty x = pathposition.getX();
        SimpleIntegerProperty y = pathposition.getY();
        Vampire v = helper.testVampireSetup(0, MAP1);
        EntityAttribute vampireAttr = v.getAttribute();
        int v_health = vampireAttr.getHealth().getValue();
        int v_attack = vampireAttr.getAttack().getValue();
        int v_CurHealth = vampireAttr.getCurHealth().getValue();

        assertEquals(v_health, 53);
        assertEquals(v_attack, 6);
        assertEquals(v_CurHealth, 53);
        Tower tower = new Tower(x, y);
        assertEquals(tower.isInRadius(v), true);
        
        List<Enemy> enemies = new List<Enemy>();
        enemies.add(v);
        tower.attackIfInRadius(enemies);
        assertEquals(v.getAttribute().getCurHealth().getValue(), 43);

        
    }






    

}
