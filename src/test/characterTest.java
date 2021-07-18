package test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Character;
import unsw.loopmania.items.Armour;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

public class characterTest {
    private static final int MAP1 = 1;
    private static final int MAP2 = 2;

    //character can be create
    @Test
    public void test_character_attr() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP1);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth().getValue();
        int attack = characterAttr.getAttack().getValue();
        int defense = characterAttr.getDefence().getValue();
        int CurHealth = characterAttr.getCurHealth().getValue();

        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);
    }

    //Character move in map1
    @Test
    public void test_character_move_map1() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP1);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        assertEquals(c.getX(), 2);
        assertEquals(c.getY(), 0);
        c.moveDownPath();
        c.moveDownPath();
        c.moveDownPath();
        c.moveDownPath();
        assertEquals(c.getX(), 4);
        assertEquals(c.getY(), 0);
        c.moveUpPath();
        assertEquals(c.getX(), 4);
        assertEquals(c.getY(), 1);

    }

    //Character move in map2
    @Test
    public void test_character_move_map2() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP2);
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
    }

    //equip and unequip
    @Test
    public void test_character_equip() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP1);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth().getValue();
        int attack = characterAttr.getAttack().getValue();
        int defense = characterAttr.getDefence().getValue();
        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        
        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        //sword
        Sword sword = new Sword(x, y);
        sword.equip(c);
        int damage_sword = sword.getDamage();
        assertEquals(attack, 5 + damage_sword);
        sword.unequip(c);
        assertEquals(attack, 5);

        //stake
        Stake stake = new Stake(x, y);
        stake.equip(c);
        int damage_stake = stake.getDamage();
        assertEquals(attack, 5 + damage_stake);
        stake.unequip(c);
        assertEquals(attack, 5);

        //staff
        Staff staff = new Staff(x, y);
        staff.equip(c);
        int damage_staff = staff.getDamage();
        assertEquals(attack, 5 + damage_staff);
        staff.unequip(c);
        assertEquals(attack, 5);

        //armour
        Armour armour = new Armour(x, y);
        armour.equip(c);
        int defense_armour = armour.getDefense();
        assertEquals(defense, 0 + defense_armour);
        armour.unequip(c);
        assertEquals(defense, 0);

        //helmet
        Helmet helmet = new Helmet(x, y);
        helmet.equip(c);
        int defense_helmet = helmet.getDefense();
        assertEquals(defense, 0 + defense_helmet);
        helmet.unequip(c);
        assertEquals(defense, 0);

        //shield
        Shield shield = new Shield(x, y);
        shield.equip(c);
        int defense_shield = shield.getDefense();
        assertEquals(defense, 0 + defense_shield);
        shield.unequip(c);
        assertEquals(defense, 0);

    }
    
    
    
    
    
    
    //add && remove soldiers
    @Test
    public void test_character_soldier() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP1);
        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        Soldier s1 = new Soldier(x, y, c);
        Soldier s2 = new Soldier(x, y, c);
        
        int numOfSoldier = c.getArmy().size();
        assertEquals(numOfSoldier, 0);
        c.addSoldier(s1);
        assertEquals(numOfSoldier, 1);
        c.addSoldier(s2);
        assertEquals(numOfSoldier, 2);
        c.remSoldier(s1);
        assertEquals(numOfSoldier, 1);
        c.remSoldier(s2);
        assertEquals(numOfSoldier, 0);
        
    }

    //Attack
    @Test
    public void test_character_attack() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP1);
        Slug slug = helper.testSlugSetup(0, MAP1);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth().getValue();
        int attack = characterAttr.getAttack().getValue();
        int defense = characterAttr.getDefence().getValue();
        int CurHealth = characterAttr.getCurHealth().getValue();

        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);
        LoopManiaWorld world = helper.createWorld(MAP1);
        int numOfEnemy = world.getEnemies().size();

        EntityAttribute slugAttr = slug.getAttribute();
        int slug_health = slugAttr.getHealth().getValue();
        int slug_attack = slugAttr.getAttack().getValue();
        assertEquals(slug_health, 3);
        assertEquals(slug_attack, 2);
        assertEquals(numOfEnemy, 1);

        c.attack(slug);
        
        assertEquals(numOfEnemy, 0);
    }



    //underAttack
    @Test
    public void test_character_underAttack() {
        Helper helper = new Helper();
        Character c = helper.testCharacterSetup(0, MAP1);
        Vampire v = helper.testVampireSetup(1, MAP1);
        EntityAttribute characterAttr = c.getAttr();
        int health = characterAttr.getHealth().getValue();
        int attack = characterAttr.getAttack().getValue();
        int defense = characterAttr.getDefence().getValue();
        int CurHealth = characterAttr.getCurHealth().getValue();

        assertEquals(health, 35);
        assertEquals(attack, 5);
        assertEquals(defense, 0);
        assertEquals(CurHealth, 35);

        EntityAttribute vampireAttr = v.getAttribute();
        int vampire_health = vampireAttr.getHealth().getValue();
        int vampire_attack = vampireAttr.getAttack().getValue();
        assertEquals(vampire_health, 32);
        assertEquals(vampire_attack, 2);

        c.underAttack(vampire_attack);
        assertEquals(CurHealth, health - vampire_attack);
    }

    //Produce soldier
    public void test_produce_soldier() {
        Helper helper = new Helper();
        Character character = helper.testCharacterSetup(0, MAP1);
        assertEuals(character.getArmy(), 0);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        Barracks barracks = new Barracks(x, y);

        barracks.soldierProducer(world);
        assertEuals(character.getArmy(), 1);
    }



    //





}
