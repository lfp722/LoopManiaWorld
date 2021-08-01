package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.goal.FinalGoal;

public class GoalTest {
    private LoopManiaWorld world;

    @Test
    public void test() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(0, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        world.getGoal().setBossGoal(10);
        world.getGoal().setCycleGoal(10);
        world.getGoal().setExpGoal(10);
        world.getGoal().setGoldGoal(10);
        
        int expected = 10;
        assertFalse(world.checkGoal());
        //boss
        world.getDefeatedBoss().set(expected);
        assertFalse(world.getGoal().checkGoal(world));
        //gold
        world.addGold(expected);
        assertFalse(world.getGoal().checkGoal(world));
        //exp
        world.addExp(expected);
        assertFalse(world.getGoal().checkGoal(world));
        //cycle 
        world.getCycle().set(expected);
        assertFalse(!world.getGoal().checkGoal(world));
    }

    @Test
    public void testToJSON() {
        FinalGoal goal = new FinalGoal();
        String expected = new String("{\"cyclegoal\":1,\"expgoal\":0,\"bossgoal\":0,\"goldgoal\":0}");
        assertEquals(expected, goal.toJSON().toString());
    }
}
