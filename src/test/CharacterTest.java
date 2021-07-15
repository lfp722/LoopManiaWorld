package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.EntityAttribute;
import unsw.loopmania.*;



public class CharacterTest {

    //The character can be created in correct stats.
    @Test
    public void test_character_attr() {
        Character c = new Character(0, [0, 0]);
        EntityAttribute characterAttr = c.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);
        assertEquals(characterAttr.getDefense(), 0);
        assertEquals(characterAttr.getCurHealth(), 35);
    }
    
    //Soldier can be add and remove
    @Test
    public void test_character_soldier() {
        Character c = new Character(0);
        int count = c.army.size();
        assertEquals(count, 0);
        c.addSoldier(1);
        c.addSoldier(2);
        assertEquals(count, 2);
        c.remSoldier(1);
        assertEquals(count, 1);
        c.remSoldier(2);
        assertEquals(count, 2);

    }
    
    
    
    
    
    //The character can move around the path correctly.
    @Test
    public void test_character_move() {
        Character c = new Character(0);
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
        Character c = new Character(0, [0, 0]);
        EntityAttribute characterAttr = c.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);
        assertEquals(characterAttr.getDefense(), 0);
        assertEquals(characterAttr.getCurHealth(), 35);

        //sword
        Sword sword = new Sword(0, 0, false);
        c.equip(sword);
        int damage_sword = sword.getDamage();
        assertEquals(characterAttr.getAttack(), 5 + damage_sword);
        c.unequip(sword);
        assertEquals(characterAttr.getAttack(), 5);

        //stake
        Stake stake = new Stake(0, 0, false);
        c.equip(stake);
        int damage_stake = stake.getDamage();
        assertEquals(characterAttr.getAttack(), 5 + damage_stake);
        c.unequip(stake);
        assertEquals(characterAttr.getAttack(), 5);

        //staff
        Staff staff = new Staff(0, 0, false);
        c.equip(staff);
        int damage_staff = staff.getDamage();
        assertEquals(characterAttr.getAttack(), 5 + damage_staff);
        c.unequip(staff);
        assertEquals(characterAttr.getAttack(), 5);

        //armour
        Armour armour = new Armour(0, 0, false);
        c.equip(armour);
        int defense_armour = armour.getDefense();
        assertEquals(characterAttr.getDefense(), 0 + defense_armour);
        c.unequip(armour);
        assertEquals(characterAttr.getDefense(), 0);

        //helmet
        Helmet helmet = new Helmet(0, 0, false);
        c.equip(helmet);
        int defense_helmet = helmet.getDefense();
        assertEquals(characterAttr.getDefense(), 0 + defense_helmet);
        c.unequip(helmet);
        assertEquals(characterAttr.getDefense(), 0);

        //shield
        Shield shield = new Shield(0, 0, false);
        c.equip(shield);
        int defense_shield = shield.getDefense();
        assertEquals(characterAttr.getDefense(), 0 + defense_shield);
        c.unequip(shield);
        assertEquals(characterAttr.getDefense(), 0);




    }

    //underAttack and attack
    @Test
    public void test_character_underAttack() {
        Character c = new Character(0, [0, 0]);
        EntityAttribute characterAttr = c.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);
        assertEquals(characterAttr.getDefense(), 0);
        assertEquals(characterAttr.getCurHealth(), 35);

        Vampire vampire = new Vampire(0, [1, 0]);
        EntityAttribute vampireAttr = vampireAttr.getAttr();
        assertEquals(vampireAttr.getHealth(), 32);
        assertEquals(vampireAttr.getAttack(), 2);

        c.attack(vampire);
        assertEquals(vampireAttr.getCurHealth(), characterAttr.getAttack());
        c.underAttack(vampireAttr.getAttack());
        assertEquals(characterAttr.getCurHealth(), 35 - vampireAttr.getAttack());
    }

    //underAttack
    @Test
    public void test_character_underAttack() {
        Character c = new Character(0, [0, 0]);
        EntityAttribute characterAttr = c.getAttr();
        assertEquals(characterAttr.getHealth(), 35);
        assertEquals(characterAttr.getAttack(), 5);
        assertEquals(characterAttr.getDefense(), 0);
        assertEquals(characterAttr.getCurHealth(), 35);

        Slug slug = new Slug(0, [1, 0]);
        EntityAttribute slugAttr = slug.getAttr();
        assertEquals(slugAttr.getHealth(), 3);
        assertEquals(slugAttr.getAttack(), 2);

        c.underAttack(slugAttr.getAttack());
        assertEquals(characterAttr.getCurHealth(), 35 - slugAttr.getAttack());
    }

    






}
