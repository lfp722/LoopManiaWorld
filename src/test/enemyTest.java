package test;

import unsw.loopmania.Slug;
import unsw.loopmania.Zombie;
import unsw.loopmania.Vampire;
import unsw.loopmania.EntityAttribute;
import test.Helper;
import unsw.loopmania.Character;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class enemyTest {
    private static final int MAP1 = 1;
    //enemy can be create correctly.
    @Test
    public void test_slug_attr() {
        Helper helper = new Helper();
        Slug s = helper.testSlugSetup(0, MAP1);
        EntityAttribute slugAttr = s.getAttribute();
        int health = slugAttr.getHealth().getValue();
        int attack = slugAttr.getAttack().getValue();
        int defense = slugAttr.getDefence().getValue();
        int CurHealth = slugAttr.getCurHealth().getValue();

        assertEquals(health, 3);
        assertEquals(attack, 2);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 3);
    }
    
    @Test
    public void test_zombie_attr() {
        Helper helper = new Helper();
        Zombie z = helper.testZombieSetup(0, MAP1);
        EntityAttribute zombieAttr = z.getAttribute();
        int health = zombieAttr.getHealth().getValue();
        int attack = zombieAttr.getAttack().getValue();
        int defense = zombieAttr.getDefence().getValue();
        int CurHealth = zombieAttr.getCurHealth().getValue();

        assertEquals(health, 3);
        assertEquals(attack, 2);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 3);
    }

    @Test
    public void test_vampire_attr() {
        Helper helper = new Helper();
        Vampire v = helper.testVampireSetup(0, MAP1);
        EntityAttribute vampireAttr = v.getAttribute();
        int health = vampireAttr.getHealth().getValue();
        int attack = vampireAttr.getAttack().getValue();
        int defense = vampireAttr.getDefence().getValue();
        int CurHealth = vampireAttr.getCurHealth().getValue();

        assertEquals(health, 32);
        assertEquals(attack, 2);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 32);

    }


    //move
    @Test
    public void test_enemy_move() {
        Helper helper = new Helper();
        Slug s = helper.testSlugSetup(0, MAP1);
        
        assertEquals(s.getX(), 0);
        assertEquals(s.getY(), 0);
        s.moveDownPath();
        assertEquals(s.getX(), 1);
        assertEquals(s.getY(), 0);
        s.moveDownPath();
        assertEquals(s.getX(), 2);
        assertEquals(s.getY(), 0);
        s.moveUpPath();
        assertEquals(s.getX(), 1);
        assertEquals(s.getY(), 0);
        s.moveUpPath();
        assertEquals(s.getX(), 0);
        assertEquals(s.getY(), 0);

    }

    //detect
    @Test
    public void test_enemy_detect() {
        Helper helper = new Helper();
        Slug s = helper.testSlugSetup(0, MAP1);
        Character c = helper.testCharacterSetup(0, MAP1);
        assertEquals(s.detect(c), true);

        s.moveDownPath();
        s.moveDownPath();
        assertEquals(s.detect(c), false);

    }

    //Attack and underattack
    public void test_enemy_attack() {
        Helper helper = new Helper();
        Vampire v = helper.testVampireSetup(0, MAP1);
        EntityAttribute vampireAttr = v.getAttribute();
        int v_health = vampireAttr.getHealth().getValue();
        int v_attack = vampireAttr.getAttack().getValue();
        int v_CurHealth = vampireAttr.getCurHealth().getValue();

        assertEquals(v_health, 32);
        assertEquals(v_attack, 2);
        assertEquals(v_CurHealth, 32);

        Character c = helper.testCharacterSetup(0, MAP1);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth().getValue();
        int attack = characterAttr.getAttack().getValue();
        int defense = characterAttr.getDefence().getValue();
        int CurHealth = characterAttr.getCurHealth().getValue();

        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(CurHealth, 35);

        v.attack(c);
        assertEquals(CurHealth, health - v_attack);

        v.underAttack(attack);
        assertEquals(v_CurHealth, v_health - attack);
    }







}
