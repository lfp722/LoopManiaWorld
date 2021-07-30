package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.Character;


public class CharacterTest {
    private LoopManiaWorld world;

    @Test
    public void testBasic() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Character ch = new Character(position, world);
        List<Soldier> army = new ArrayList<>();
        ch.setArmy(army);
        assertEquals(ch.getArmy(), army);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        Soldier s = new Soldier(x, y, ch);
        ch.addSoldier(s);
        assertEquals(ch.getArmy().get(0), s);
        assertEquals(ch.getArmy().size(), 1);
        ch.remSoldier(s);
        assertEquals(ch.getArmy().size(), 0);
        boolean expectedStunned = true;
        assertEquals(ch.isStunned(), !expectedStunned);
        ch.setStunned(expectedStunned);
        assertEquals(ch.isStunned(), expectedStunned);
        EntityAttribute expectedAttr = new EntityAttribute(5, 1, 35);
        assertEquals(ch.getAttr().getAttack().get(), expectedAttr.getAttack().get());
        assertEquals(ch.getAttr().getDefence().get(), expectedAttr.getDefence().get());
        assertEquals(ch.getAttr().getHealth().get(), expectedAttr.getHealth().get());
        assertEquals(ch.getAttr().getCurHealth().get(), expectedAttr.getCurHealth().get());
        int expectedGold = 0;
        assertEquals(ch.getGold(), expectedGold);
        assertEquals(ch.getG().get(), expectedGold);
        expectedGold = 10;
        ch.addGold(expectedGold);
        assertEquals(ch.getGold(), expectedGold);
        assertEquals(ch.getG().get(), expectedGold);
        int expectedExp = 0;
        assertEquals(ch.getExp(), expectedExp);
        assertEquals(ch.getExperience().get(), expectedExp);
        expectedExp = 10;
        ch.addExp(expectedExp);
        assertEquals(ch.getExp(), expectedExp);
        assertEquals(ch.getExperience().get(), expectedExp);
        assertEquals(ch.getEquip(), world.getEquip());
        List<Enemy> expectedTranced = new ArrayList<>();
        assertEquals(ch.getTranced(), expectedTranced);
        int expectedBuff = 1;
        assertEquals(expectedBuff, ch.getCampFireBuff().get());
        assertEquals(expectedBuff, ch.getStakeVampireBuff().get());
        assertEquals(expectedBuff, ch.getDebuff().get());
        int expectedLv = 1;
        assertEquals(expectedLv, ch.getLevel().get());
        int expectedPoint = 1;
        assertEquals(expectedPoint, ch.getAttackPoints().get());
        assertEquals(expectedPoint, ch.getDefencePoints().get());
        assertEquals(expectedPoint, ch.getHealthPoints().get());
        int expectedNextExp = 6000;
        assertEquals(expectedNextExp, ch.getNextLvExp().get());
        assertEquals(expectedBuff, ch.getTsBuff().get());
    }

    @Test
    public void testLvAndPoint() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Character ch = new Character(position, world);
        world.setCharacter(ch);
        ch.getExperience().set(100000);
        int expectLv = 6;
        assertEquals(expectLv, ch.getLevel().get());
        int expectedPoint = 5;
        int expectedattr = 7;
        int expectedDefence = 2;
        int expectedHealth = 55;
        assertEquals(expectedPoint, ch.getTechPoints().get());
        ch.addAttackPoints();
        assertEquals(expectedattr, ch.getAttr().getAttack().get());
        assertEquals(expectedPoint - 1, ch.getTechPoints().get());
        ch.addDefencePoints();
        assertEquals(expectedDefence, ch.getAttr().getDefence().get());
        assertEquals(expectedPoint - 2, ch.getTechPoints().get());
        ch.addHealthPoints();
        assertEquals(expectedHealth, ch.getAttr().getHealth().get());
        assertEquals(expectedPoint - 3, ch.getTechPoints().get());
    }

    @Test
    public void testTech() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Character ch = new Character(position, world);
        ch.getExperience().set(300000);
        int expectLv = 11;
        assertEquals(expectLv, ch.getLevel().get());
        int expectedPoint = 10;
        int expectedattr = 25;
        int expectedDefence = 2;
        int expectedHealth = 55;
        int expectedRage = 1;
        int expectedAngry = 2;
        assertEquals(expectedPoint, ch.getTechPoints().get());
        for (int i = 0; i < 10; i++) {
            ch.addAttackPoints();
        }
        assertEquals(expectedattr, ch.getAttr().getAttack().get());
        assertEquals(expectedPoint - 10, ch.getTechPoints().get());
        assertEquals(expectedRage, ch.getRage().get());
        ch.getAttr().getCurHealth().set(1);
        assertEquals(expectedAngry, ch.getRage().get());

        Character cha = new Character(position, world);
        cha.getExperience().set(300000);
        expectedPoint = 10;
        expectedDefence = 11;
        assertEquals(expectedPoint, cha.getTechPoints().get());
        for (int i = 0; i < 10; i++) {
            cha.addDefencePoints();
        }
        assertEquals(expectedDefence, cha.getAttr().getDefence().get());
        assertEquals(expectedPoint - 10, cha.getTechPoints().get());
        assertFalse(!cha.isCounterMiss());


        Character chara = new Character(position, world);
        world.setCharacter(chara);
        world.getCharacter().getExperience().set(300000);
        expectedPoint = 10;
        expectedHealth = 235;
        assertEquals(expectedPoint, chara.getTechPoints().get());
        assertFalse(chara.isHeal());
        for (int i = 0; i < 10; i++) {
            chara.addHealthPoints();
        }
        assertEquals(expectedHealth, chara.getAttr().getHealth().get());
        assertEquals(expectedPoint - 10, chara.getTechPoints().get());
        assertFalse(!chara.isHeal());
        world.getCharacter().getAttr().getCurHealth().set(1);
        chara.getPosition().setCurrentPositionInPath(world.getOrderedPath().size() - 1);
        world.runTickMoves();
        assertEquals(51, chara.getAttr().getCurHealth().get());


        // ch.addDefencePoints();
        // assertEquals(expectedDefence, ch.getAttr().getDefence().get());
        // assertEquals(expectedPoint - 2, ch.getTechPoints().get());
        // ch.addHealthPoints();
        // assertEquals(expectedHealth, ch.getAttr().getHealth().get());
        // assertEquals(expectedPoint - 3, ch.getTechPoints().get());

    }

    @Test
    public void testToJSON() {
        this.world = Helper.createWorld();
        PathPosition position = new PathPosition(1, world.getOrderedPath());
        Character ch = new Character(position, world);
        String expect = new String("{\"gold\":0,\"army\":[],\"attpoints\":1,\"defpoints\":1,\"healthpoints\":1,\"position\":1,\"curHealth\":35,\"exp\":0}");
        assertEquals(expect, ch.characterToJson().toString());
    }
}
