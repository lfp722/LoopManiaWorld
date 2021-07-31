package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class PathPositionTest {
    @Test
    public void test() {
        LoopManiaWorld world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        List<Pair<Integer, Integer>> expected = new ArrayList<>();
        position.setOrderedPath(expected);
        assertEquals(expected, position.getOrderedPath());
    }

    @Test
    public void testToJSON() {
        LoopManiaWorld world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        String expected = new String("{\"path\":[\"RIGHT\",\"RIGHT\",\"DOWN\",\"RIGHT\",\"RIGHT\",\"UP\",\"RIGHT\",\"RIGHT\",\"RIGHT\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"LEFT\",\"LEFT\",\"LEFT\",\"UP\",\"UP\",\"RIGHT\",\"UP\",\"UP\",\"UP\",\"UP\",\"LEFT\",\"LEFT\",\"LEFT\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"DOWN\",\"RIGHT\",\"RIGHT\",\"RIGHT\",\"RIGHT\",\"RIGHT\",\"DOWN\",\"DOWN\",\"LEFT\",\"LEFT\",\"LEFT\",\"LEFT\",\"LEFT\",\"LEFT\",\"LEFT\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\",\"UP\"],\"x\":0,\"y\":0,\"type\":\"path_tile\"}");
        assertEquals(expected, position.toJSON().toString());
    }
}
