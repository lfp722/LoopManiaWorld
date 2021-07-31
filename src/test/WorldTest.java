package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.javatuples.Pair;
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
}
