package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Soldier;

public class SoldierTest {
    @Test
    public void test() {
        LoopManiaWorld world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        Character ch = new Character(position, world);
        Soldier s = new Soldier(x, y, ch);
        String expected = new String("{\"x\":0,\"y\":0,\"attr\":{\"defence\":1,\"attack\":2,\"health\":8,\"curHealth\":8}}");
        assertEquals(expected, s.toJSON().toString());
    }
}
