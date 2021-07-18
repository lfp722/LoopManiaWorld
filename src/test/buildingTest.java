package test;


import test.Helper;
import unsw.loopmania.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

public class buildingTest {
    private static final int CURRENT = 0;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;
    private static final int MAP1 = 1;

    //add buiding
    @Test
    public void test_building_create() {    
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(MAP1);
        int count = world.getBuildingEntity().size();
        Building building = helper.createBuildingSetup("Barrack", 30, world, CURRENT);
        assertEquals(building.getX(), 3);
        assertEquals(building.getY(), 3);
        assertEquals(count, 1);

        building = helper.createBuildingSetup("VampireCastle", 30, world, NORTH);
        assertEquals(building.getX(), 3);
        assertEquals(building.getY(), 2);
        assertEquals(count, 2);
        
        building = helper.createBuildingSetup("ZombiePit", 30, world, SOUTH);
        assertEquals(building.getX(), 3);
        assertEquals(building.getY(), 4);
        assertEquals(count, 2);

        building = helper.createBuildingSetup("Village", 30, world, EAST);
        assertEquals(4, building.getX());
        assertEquals(3, building.getY());
        assertEquals(count, 3);

        building = helper.createBuildingSetup("Trap", 30, world, WEST);
        assertEquals(building.getX(), 2);
        assertEquals(building.getY(), 3);
        assertEquals(count, 4);

        building = helper.createBuildingSetup("Campfire", 30, world, WEST);
        assertEquals(building.getX(), 2);
        assertEquals(building.getY(), 3);
        assertEquals(count, 5);

    }
    

}
