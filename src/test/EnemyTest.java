package test;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import unsw.loopmania.EntityAttribute;
import unsw.loopmania.Enemy;
import unsw.loopmania.Character;


public class EnemyTest {

    //The Enemy can be created in correct stats.
    @Test
    public void test_character_attr() {
        //slug
        Slug slug = new Slug(0, [0, 0]);
        EntityAttribute slugAttr = slug.getAttr();
        assertEquals(slugAttr.getHealth(), 3);
        assertEquals(slugAttr.getAttack(), 2);
        assertEquals(slugAttr.getDefense(), 0);
        assertEquals(slugAttr.getCurHealth(), 3);

        //zombie
        Zombie zombie = new Zombie(0, [0, 0]);
        EntityAttribute zombieAttr = zombie.getAttr();
        assertEquals(zombieAttr.getHealth(), 5);
        assertEquals(zombieAttr.getAttack(), 6);
        assertEquals(zombieAttr.getDefense(), 0);
        assertEquals(zombieAttr.getCurHealth(), 5);

        //Vampire
        Vampire vampire = new Vampire(0, [0, 0]);
        EntityAttribute vampireAttr = vampire.getAttr();
        assertEquals(vampireAttr.getHealth(), 32);
        assertEquals(vampireAttr.getAttack(), 2);
        assertEquals(vampireAttr.getDefense(), 0);
        assertEquals(vampireAttr.getCurHealth(), 32);
    }

    @Test
    public void test_enemy_attack() {
        //slug
        Slug slug = new Slug(0, [0, 0]);
        EntityAttribute slugAttr = slug.getAttr();
        assertEquals(slugAttr.getHealth(), 3);
        assertEquals(slugAttr.getAttack(), 2);

        Character c1 = new Character(0);
        EntityAttribute characterAttr = c1.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);

        slug.attack(c1);
        assertEquals(characterAttr.getHealth(), 33);


        //zombie
        Zombie zombie = new Zombie(0, [0, 0]);
        EntityAttribute zombieAttr = zombie.getAttr();
        assertEquals(zombieAttr.getHealth(), 5);
        assertEquals(zombieAttr.getAttack(), 6);

        Character c2 = new Character(0);
        EntityAttribute characterAttr = c2.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);

        zombie.attack(c2);
        assertEquals(characterAttr.getHealth(), 29);

        //Vampire
        Vampire vampire = new Vampire(0, [0, 0]);
        EntityAttribute vampireAttr = vampire.getAttr();
        assertEquals(vampireAttr.getHealth(), 32);
        assertEquals(vampireAttr.getAttack(), 2);

        Character c3 = new Character(0);
        EntityAttribute characterAttr = c3.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);

        zombie.attack(c3);
        assertEquals(characterAttr.getHealth(), 33);
    }

    //Detect if an character is in the range of detection
    @Test
    public void test_enemy_detect() {
        //slug
        Character c = new Character(0, [0, 0]);
        Slug slug1 = new Slug(0, [2, 0]);
        assertEquals(slug1.detect(c), false);

        Slug slug2 = new Slug(0, [1, 0]);
        assertEquals(slug2.detect(c), true);

        //zombie
        Zombie zombie1 = new zombie(0, [4, 0]);
        assertEquals(zombie1.detect(c), false);

        Zombie zombie2 = new zombie(0, [1, 0]);
        assertEquals(zombie2.detect(c), true);

        //Vampire
        Vampire vampire = new vampire(0, [4, 0]);
        assertEquals(vampire1.detect(c), false);

        Vampire vampire = new vampire(0, [1, 0]);
        assertEquals(vampire2.detect(c), true);

    }

    




    
}
