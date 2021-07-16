package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.util.Pair;
import unsw.loopmania.Character;
import unsw.loopmania.EntityAttribute;
import unsw.loopmania.*;



public class CharacterTest {

    //The character can be created in correct stats.
    @Test
    public void test_character_attr() {
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0, orderedPath);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth();
        int attack = characterAttr.getAttack();
        int defense = characterAttr.getDefense();
        int CurHealth = characterAttr.getCurHealth();
        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);
    }
    
    //Soldier can be add and remove
    @Test
    public void test_character_soldier() {
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0, orderedPath);
        Soldier s1 = new Soldier(0, 0, c);
        Soldier s2 = new Soldier(0, 0, c);
        int count = c.army.size();
        assertEquals(count, 0);
        c.addSoldier(s1);
        assertEquals(count, 1);
        c.addSoldier(s2);
        assertEquals(count, 2);
        c.remSoldier(s1);
        assertEquals(count, 1);
        c.remSoldier(s2);
        assertEquals(count, 0);

    }
    
    
    
    
    
    //The character can move around the path correctly.
    @Test
    public void test_character_move() {
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0, orderedPath);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        assertEquals(c.getX(), 2);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        assertEquals(c.getX(), 2);
        assertEquals(c.getY(), 1);

        c.moveUpPath();
        assertEquals(c.getX(), 2);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), 0);
    }

    //Test equip and unequip
    @Test
    public void test_character_equip() {
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0, orderedPath);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth();
        int attack = characterAttr.getAttack();
        int defense = characterAttr.getDefense();
        int CurHealth = characterAttr.getCurHealth();
        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);

        //sword
        Sword sword = new Sword(0, 0, false);
        c.equip(sword);
        int damage_sword = sword.getDamage();
        assertEquals(attack, 5 + damage_sword);
        c.unequip(sword);
        assertEquals(attack, 5);

        //stake
        Stake stake = new Stake(0, 0, false);
        c.equip(stake);
        int damage_stake = stake.getDamage();
        assertEquals(attack, 5 + damage_stake);
        c.unequip(stake);
        assertEquals(attack, 5);

        //staff
        Staff staff = new Staff(0, 0, false);
        c.equip(staff);
        int damage_staff = staff.getDamage();
        assertEquals(attack, 5 + damage_staff);
        c.unequip(staff);
        assertEquals(attack, 5);

        //armour
        Armour armour = new Armour(0, 0, false);
        c.equip(armour);
        int defense_armour = armour.getDefense();
        assertEquals(defense, 0 + defense_armour);
        c.unequip(armour);
        assertEquals(defense, 0);

        //helmet
        Helmet helmet = new Helmet(0, 0, false);
        c.equip(helmet);
        int defense_helmet = helmet.getDefense();
        assertEquals(defense, 0 + defense_helmet);
        c.unequip(helmet);
        assertEquals(defense, 0);

        //shield
        Shield shield = new Shield(0, 0, false);
        c.equip(shield);
        int defense_shield = shield.getDefense();
        assertEquals(defense, 0 + defense_shield);
        c.unequip(shield);
        assertEquals(defense, 0);




    }

    //underAttack and attack
    @Test
    public void test_character_Attack() {
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0, orderedPath);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth();
        int attack = characterAttr.getAttack();
        int defense = characterAttr.getDefense();
        int CurHealth = characterAttr.getCurHealth();
        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);
        
        List<Pair<Integer, Integer>> vampire_orderedPath = new List<Pair<1, 0>>;
        Vampire vampire = new Vampire(0, vampire_orderedPath);
        EntityAttribute vampireAttr = vampireAttr.getAttr();
        int vampire_health = vampireAttr.getHealth();
        int vampire_attack = vampireAttr.getAttack();
        int cur_vampire_health = vampireAttr.getCurHealth();
        assertEquals(vampire_health, 32);
        assertEquals(vampire_attack, 2);

        c.attack(vampire);
        assertEquals(cur_vampire_health, vampire_health - attack);
    }

    //underAttack
    @Test
    public void test_character_underAttack() {
        List<Pair<Integer, Integer>> orderedPath = new List<Pair<0, 0>>;
        Character c = new Character(0, orderedPath);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth();
        int attack = characterAttr.getAttack();
        int defense = characterAttr.getDefense();
        int CurHealth = characterAttr.getCurHealth();
        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);
        
        List<Pair<Integer, Integer>> slug_orderedPath = new List<Pair<1, 0>>;
        Slug slug = new Slug(0, slug_orderedPath);
        EntityAttribute slugAttr = slug.getAttr();
        int slug_health = slugAttr.getHealth();
        int slug_attack = slugAttr.getAttack();
        assertEquals(slug_health, 3);
        assertEquals(slug_attack, 2);

        c.underAttack(slug_attack);
        assertEquals(CurHealth, health - slug_attack);
    }

    






}
