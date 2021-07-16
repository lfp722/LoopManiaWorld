package test;

import org.junit.Test;

import javafx.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.EntityAttribute;
import unsw.loopmania.Enemy;
import unsw.loopmania.Character;


public class EnemyTest {

    //The Enemy can be created in correct stats.
    @Test
    public void test_character_attr() {
        //slug
        List<Pair<Integer, Integer>> slug_orderedPath = new List<Pair<1, 0>>;
        Slug slug = new Slug(0, slug_orderedPath);
        EntityAttribute slugAttr = slug.getAttr();
        int slug_health = slugAttr.getHealth();
        int slug_attack = slugAttr.getAttack();
        int slug_defense = slugAttr.getDefense();
        int slug_CurHealth = slugAttr.getCurHealth();
        assertEquals(slug_health, 3);
        assertEquals(slug_attack, 2);
        assertEquals(slug_defense, 0);
        assertEquals(slug_CurHealth, 3);

        //zombie
        List<Pair<Integer, Integer>> zombie_orderedPath = new List<Pair<1, 0>>;
        Zombie zombie = new Zombie(0, zombie_orderedPath);
        EntityAttribute zombieAttr = zombie.getAttr();
        int zombie_health = slugAttr.getHealth();
        int zombie_attack = slugAttr.getAttack();
        int zombie_defense = slugAttr.getDefense();
        int zombie_CurHealth = slugAttr.getCurHealth();
        assertEquals(zombie_health, 5);
        assertEquals(zombie_attack, 6);
        assertEquals(zombie_defense, 0);
        assertEquals(zombie_CurHealth, 5);

        //Vampire
        List<Pair<Integer, Integer>> vampire_orderedPath = new List<Pair<1, 0>>;
        Vampire vampire = new Vampire(0, vampire_orderedPath);
        EntityAttribute vampireAttr = vampire.getAttr();
        int vampire_health = slugAttr.getHealth();
        int vampire_attack = slugAttr.getAttack();
        int vampire_defense = slugAttr.getDefense();
        int vampire_CurHealth = slugAttr.getCurHealth();
        assertEquals(vampire_health, 32);
        assertEquals(vampire_attack, 2);
        assertEquals(vampire_defense, 0);
        assertEquals(vampire_CurHealth, 32);
    }

    @Test
    public void test_enemy_attack() {
        //slug
        List<Pair<Integer, Integer>> slug_orderedPath = new List<Pair<1, 0>>;
        Slug slug = new Slug(0, slug_orderedPath);
        EntityAttribute slugAttr = slug.getAttr();
        int slug_health = slugAttr.getHealth();
        int slug_attack = slugAttr.getAttack();
        int slug_CurHealth = slugAttr.getCurHealth();
        assertEquals(slug_health, 3);
        assertEquals(slug_attack, 2);
        assertEquals(slug_CurHealth, 3);

        List<Pair<Integer, Integer>> c1_orderedPath = new List<Pair<0, 0>>;
        Character c1 = new Character(0, c1_orderedPath);
        EntityAttribute c1Attr = c1.getAttr();
        int c1_health = c1Attr.getHealth();
        int c1_attack = c1Attr.getAttack();
        int c1_CurHealth = c1Attr.getCurHealth();
        assertEquals(c1_health, 35);
        assertEquals(c1_attack, 5);
        assertEquals(c1_CurHealth, 35);

        slug.attack(c1);
        assertEquals(c1_CurHealth, c1_health - slug_attack);


        //zombie
        List<Pair<Integer, Integer>> zombie_orderedPath = new List<Pair<1, 0>>;
        Zombie zombie = new Zombie(0, zombie_orderedPath);
        EntityAttribute zombieAttr = zombie.getAttr();
        int zombie_health = zombieAttr.getHealth();
        int zombie_attack = zombieAttr.getAttack();
        int zombie_CurHealth = zombieAttr.getCurHealth();
        assertEquals(zombie_health, 5);
        assertEquals(zombie_attack, 6);
        assertEquals(zombie_CurHealth, 5);

        List<Pair<Integer, Integer>> c2_orderedPath = new List<Pair<0, 0>>;
        Character c2 = new Character(0, c2_orderedPath);
        EntityAttribute c2Attr = c2.getAttr();
        int c2_health = c2Attr.getHealth();
        int c2_attack = c2Attr.getAttack();
        int c2_CurHealth = c2Attr.getCurHealth();
        assertEquals(c2_health, 35);
        assertEquals(c2_attack, 5);
        assertEquals(c2_CurHealth, 35);

        zombie.attack(c2);
        assertEquals(c2_CurHealth, c2_health - zombie_attack);

        //Vampire
        List<Pair<Integer, Integer>> vampire_orderedPath = new List<Pair<1, 0>>;
        Vampire vampire = new Vampire(0, vampire_orderedPath);
        EntityAttribute vampireAttr = vampire.getAttr();
        int vampire_health = vampireAttr.getHealth();
        int vampire_attack = vampireAttr.getAttack();
        int vampire_CurHealth = vampireAttr.getCurHealth();
        assertEquals(vampire_health, 32);
        assertEquals(vampire_attack, 2);
        assertEquals(vampire_CurHealth, 32);

        List<Pair<Integer, Integer>> c3_orderedPath = new List<Pair<0, 0>>;
        Character c3 = new Character(0, c3_orderedPath);
        EntityAttribute c3Attr = c3.getAttr();
        int c3_health = c3Attr.getHealth();
        int c3_attack = c3Attr.getAttack();
        int c3_CurHealth = c3Attr.getCurHealth();
        assertEquals(c3_health, 35);
        assertEquals(c3_attack, 5);
        assertEquals(c3_CurHealth, 35);

        vampire.attack(c3);
        assertEquals(c3_CurHealth, c3_health - vampire_attack);
    }

    //Detect if an character is in the range of detection
    @Test
    public void test_enemy_detect() {
        //slug
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0,orderedPath);
        List<Pair<Integer, Integer>> slug1_orderedPath = new List<Pair<2, 0>>;
        Slug slug1 = new Slug(0, slug1_orderedPath);
        assertEquals(slug1.detect(c), false);
        
        List<Pair<Integer, Integer>> slug2_orderedPath = new List<Pair<1, 0>>;
        Slug slug2 = new Slug(0, slug2_orderedPath);
        assertEquals(slug2.detect(c), true);

        //zombie
        List<Pair<Integer, Integer>> zombie1_orderedPath = new List<Pair<4, 0>>;
        Zombie zombie1 = new zombie(0, zombie1_orderedPath);
        assertEquals(zombie1.detect(c), false);

        List<Pair<Integer, Integer>> zombie2_orderedPath = new List<Pair<1, 0>>;
        Zombie zombie2 = new zombie(0, zombie2_orderedPath);
        assertEquals(zombie2.detect(c), true);

        //Vampire
        List<Pair<Integer, Integer>> vampire1_orderedPath = new List<Pair<4, 0>>;
        Vampire vampire = new vampire(0, vampire1_orderedPath);
        assertEquals(vampire1.detect(c), false);

        List<Pair<Integer, Integer>> vampire2_orderedPath = new List<Pair<1, 0>>;
        Vampire vampire = new vampire(0, vampire2_orderedPath);
        assertEquals(vampire2.detect(c), true);

    }

    




    
}
