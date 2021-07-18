package test;


import test.Helper;
import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.Barrack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

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
        Building barrack = helper.createBuildingSetup("Barrack", 1, world, CURRENT);
        Character character = helper.testCharacterSetup(1, MAP1);
        Soldier soldier = barrack.soldierProducer(world);

    }
    

}
