package unsw.loopmania;
import unsw.loopmania.goal.FinalGoal;
import unsw.loopmania.items.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    public static final int equippedHeight = 1;
    public static final int weaponSlot = 0;
    public static final int helmetSlot = 1;
    public static final int shieldSlot = 2;
    public static final int armourSlot = 1;

    public static final int helmetHeight = 0;

    public static final int soldierHeight = 0;
    public static final int soldierWidth = 4;

    public static final int ringHeight = 0;
    public static final int ringWidth = 0;

    public static int NORMAL = 0;
    public static int BERSERKER = 1;
    public static int SURVIVAL = 2;
    public static int CONFUSING = 3;

    private int mode = NORMAL;

    private SimpleIntegerProperty doggiePrice;

    private SimpleIntegerProperty battleLock = new SimpleIntegerProperty(1);

    /**
     * width of the world in GridPane cells
     */
    private int width;

    private boolean theOneRingExist = false;
    private boolean andurilExist = false;
    private boolean treeStumpExist = false;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    private List<Item> boughtItems = new ArrayList<>();

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    //private List<Entity> nonSpecifiedEntities;

    private Character character;
    private List<Enemy> enemies;

    private List<Card> cardEntities;

    private List<Item> unequippedInventoryItems;

    private List<Building> buildingEntities;

    private Equipped equippedItems;

    private SimpleIntegerProperty cycle;

    //max number of enemy
    private SimpleIntegerProperty maxNumTotal;

    private List<Item> spawnItems;

    private SimpleIntegerProperty defeatedBoss;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private FinalGoal goal;

    private String pathOfMap;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        //nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        spawnItems = new ArrayList<>();
        cycle = new SimpleIntegerProperty(1);
        equippedItems = new Equipped(this);
        maxNumTotal = new SimpleIntegerProperty();
        maxNumTotal.bind(Bindings.createIntegerBinding(()->getCycle().multiply(2).add(5).get()));
        goal = new FinalGoal();
        doggiePrice = new SimpleIntegerProperty(5000);
        defeatedBoss = new SimpleIntegerProperty(0);
    }

    /**
     * getters 
     * @return
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void allowTheOneRing() {
        this.theOneRingExist = true;
    }

    public void allowAnduril() {
        this.andurilExist = true;
    }

    public void allowTreeStump() {
        this.treeStumpExist = true;
    }

    public boolean isTheOneRing() {
        return theOneRingExist;
    }

    public FinalGoal getGoal() {
        return goal;
    }

    public void setPath(String path) {
        this.pathOfMap = path;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getPath() {
        return pathOfMap;
    }

    public SimpleIntegerProperty getDoggiePrice() {
        return doggiePrice;
    }


    public TheOneRing addEquippedRing() {
        if (equippedItems.getRing() != null) {
            equippedItems.getRing().destroy();
        }
        TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(ringWidth), new SimpleIntegerProperty(ringHeight), isConfusing());
        equippedItems.equipRing(ring);
        return ring;
    }

    public SimpleIntegerProperty getCycle() {
        return cycle;
    }

    public SimpleIntegerProperty getMaxNumTotal() {
        return maxNumTotal;
    }

    

    public List<Card> getCardEntities() {
        return cardEntities;
    }

    public Equipped getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(Equipped equippedItems) {
        this.equippedItems = equippedItems;
    }

    public List<Item> getSpawnItems() {
        return spawnItems;
    }

    public void addEnemy(Enemy e) {
        this.enemies.add(e);
    }

    public List<Building> getBuildingEntities() {
        return buildingEntities;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    // public void addEntity(Entity entity) {
    //     // for adding non-specific entities (ones without another dedicated list)
    //     nonSpecifiedEntities.add(entity);
    // }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<Slug> possiblySpawnEnemies(){
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<Slug> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Slug enemy = new Slug(new PathPosition(indexInPath, orderedPath), (new Random()).nextInt(cycle.get()));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }
        return spawningEnemies;
    }

    public List<Doggie> possiblySpawnDoggies(){
        Pair<Integer, Integer> pos = possiblyGetDoggieSpawnPosition();
        List<Doggie> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Doggie enemy = new Doggie(new PathPosition(indexInPath, orderedPath), (new Random()).nextInt(cycle.get()));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }
        return spawningEnemies;
    }

    public List<ElanMuske> possiblySpawnElans(){
        Pair<Integer, Integer> pos = possiblyGetElanSpawnPosition();
        List<ElanMuske> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            ElanMuske enemy = new ElanMuske(new PathPosition(indexInPath, orderedPath), (new Random()).nextInt(cycle.get()));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
            enemy.increaseDoggiePrice(this);
        }
        return spawningEnemies;
    }

    public List<Zombie> possiblySpawnZombies(){
        List<Zombie> spawningEnemies = new ArrayList<>();
        for (Building b: getBuildingEntities()) {
            if (b instanceof ZombiePit) {
                ZombiePit a = (ZombiePit) b;
                Zombie z = a.produceZombie(this);
                if (z != null) {
                    spawningEnemies.add(z);
                }
                
            }
        }
        return spawningEnemies;
    }

    public List<Vampire> possiblySpawnVampire(){
        List<Vampire> spawningEnemies = new ArrayList<>();
        for (Building b: getBuildingEntities()) {
            if (b instanceof VampireCastle) {
                VampireCastle a = (VampireCastle) b;
                Vampire v = a.produceVampire(this);
                if (v != null) {
                    spawningEnemies.add(v);
                }
                
            }
        }
        return spawningEnemies;
    }

    /**
     * also spawn gold by different possibility from enemy
     * @return
     */
    public List<Gold> possiblySpawnGold(){
        Pair<Integer, Integer> pos = possiblyGetGoldPosition();
        List<Gold> spawningItems = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            int value = new Random().nextInt(40)+10;
            Gold i = new Gold((new PathPosition(indexInPath, orderedPath).getX()), (new PathPosition(indexInPath, orderedPath).getY()), value);
            //nonSpecifiedEntities.add(i);
            spawningItems.add(i);
            spawnItems.add(i);
        }
        return spawningItems;
    }

    /**
     * similar as gold
     * @return
     */
    public List<Potion> possiblySpawnPotion(){
        Pair<Integer, Integer> pos = possiblyGetPosionPosition();
        List<Potion> spawningItems = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Potion i = new Potion((new PathPosition(indexInPath, orderedPath).getX()), (new PathPosition(indexInPath, orderedPath).getY()));
            //nonSpecifiedEntities.add(i);
            spawningItems.add(i);
            spawnItems.add(i);
        }
        return spawningItems;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * battle simulator
     * @param battleEnemies
     * @param defeatedEnemies
     * @param ch
     */
    private void battle(List<Enemy> battleEnemies, List<Enemy> defeatedEnemies, Character ch) {
        // if (!ch.shouldExist().get()) {
        //     System.out.println("I am dead!");
        // }
        
        while (!battleEnemies.isEmpty() && ch.shouldExist().get()) {
            for (Soldier s: ch.getArmy()) {
                Enemy target = battleEnemies.get(0);
                s.attack(target);
                //System.out.println("Soldier attack enemy");
                if (!target.shouldExist().get()) {
                    battleEnemies.remove(target);
                    defeatedEnemies.add(target);
                    //System.out.println("Enemy defeated");
                    if (battleEnemies.isEmpty()) { 
                        break; 
                    }
                }
            }

            if (battleEnemies.isEmpty()) {
                for (Enemy e: ch.getTranced()) {
                    e.destroy();
                    defeatedEnemies.add(e);
                }
                ch.getTranced().clear();
            }

            for (Enemy s: ch.getTranced()) {
                Enemy target = battleEnemies.get(0);
                s.attack(target);
                //System.out.println("Tranced enemy attack enemy");
                if (!target.shouldExist().get()) {
                    
                    battleEnemies.remove(target);
                    defeatedEnemies.add(target);
                    //System.out.println("Enemy defeated");
                    if (battleEnemies.isEmpty()) { 
                        break; 
                    }
                }
            }

            if (battleEnemies.isEmpty()) {
                for (Enemy e: ch.getTranced()) {
                    e.destroy();
                    defeatedEnemies.add(e);
                }
                ch.getTranced().clear();;
            }
            // for (Enemy e: ch.getTranced()) {
            //     e.attack(target);
            // }
            if (!battleEnemies.isEmpty()) {
                Enemy target = battleEnemies.get(0);
                ch.attack(target);
                //System.out.println("Character attack enemy");
                if (!target.shouldExist().get()) {
                    battleEnemies.remove(target);
                    defeatedEnemies.add(target);
                    //System.out.println("Enemy defeated");
                }
            }

            if (battleEnemies.isEmpty()) {
                for (Enemy e: ch.getTranced()) {
                    e.destroy();
                    defeatedEnemies.add(e);
                }
                ch.getTranced().clear();
            }

            for (Enemy e: battleEnemies) {
                if (ch.getArmy().isEmpty()) {
                    e.attack(ch);
                    if (e instanceof ElanMuske) {
                        ElanMuske elan = (ElanMuske) e;
                        elan.healEnemy(battleEnemies);
                    }
                    //System.out.println("Enemy attack character");
                }
                else{
                    Soldier brave = ch.getArmy().get(0);
                    e.attack(brave, this);
                    if (e instanceof ElanMuske) {
                        ElanMuske elan = (ElanMuske) e;
                        elan.healEnemy(battleEnemies);
                    }
                    //System.out.println("Enemy attack soldier");
                }
            }

        }
    }

    /**
     * battle loader
     * @return a list of enemy been defeated during a battle
     */
    public List<Enemy> runBattles() {
        //System.out.println("battle run!");
        if (battleLock.get() == 0) {
            return new ArrayList<>();
        }
        battleLock.set(0);
        //System.out.println("Running battles");
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        List<Enemy> battleEnemies = new ArrayList<>();
        boolean isBattle = false;
        Character character = getCharacter();


        for (Enemy e: getEnemies()){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            if (isBattle) {
                if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < e.getSupportRange()){
                    // fight...
                    battleEnemies.add(e);
                }
            }
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < e.getDetectRange()){
                // fight...
                battleEnemies.add(e);
                isBattle = true;
            }
        }
        for (Building b: getBuildingEntities()) {
            if (b instanceof Tower) {
                Tower t = (Tower) b;
                t.attackIfInRadius(battleEnemies);
            }
        }
        System.out.println("battle enemy size: " + battleEnemies.size());
        battle(battleEnemies, defeatedEnemies, character);
        character.getStakeVampireBuff().set(1);
        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            if (e.getBoss()) {
                defeatBoss();
            }
            killEnemy(e);
        }
        battleLock.set(1);
        
        return defeatedEnemies;

    }

    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public VampireCastleCard loadVampireCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    public VillageCard loadVillageCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        VillageCard vampireCastleCard = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    public BarrackCard loadBarrackCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        BarrackCard vampireCastleCard = new BarrackCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    public ZombiePitCard loadZombieCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        ZombiePitCard vampireCastleCard = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    public TowerCard loadTowerCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        TowerCard vampireCastleCard = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    public TrapCard loadTrapCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        TrapCard vampireCastleCard = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    public CampFireCard loadCampFireCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            removeCard(0);
        }
        CampFireCard vampireCastleCard = new CampFireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * remove solder at a particular index of soldiers (position in gridpane of unplayed soldiers)
     * @param index the index of the soldier, from 0 to length-1
     */
    public void removeSoldier(int index){
        Soldier c = character.getArmy().get(index);
        int x = c.getX();
        c.destroy();
        character.getArmy().remove(index);
        shiftSoldiersDownFromXCoordinate(x);
    }

    /**
     * spawn a sword in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Sword addUnequippedSword(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Armour addUnequippedArmour(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Armour sword = new Armour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Helmet addUnequippedHelmet(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Helmet sword = new Helmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Shield addUnequippedShield(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Shield sword = new Shield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Stake addUnequippedStake(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Stake sword = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Staff addUnequippedStaff(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Staff sword = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Anduril addUnequippedAnduril(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Anduril anduril = new Anduril(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), isConfusing());
        unequippedInventoryItems.add(anduril);
        return anduril;
    }

    public TreeStump addUnequippedTreeStump(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        TreeStump ts = new TreeStump(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), isConfusing());
        unequippedInventoryItems.add(ts);
        return ts;
    }

    public Potion addPotion(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Potion sword = new Potion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public DoggieCoin addDoggie(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        DoggieCoin sword = new DoggieCoin(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), this.doggiePrice);
        unequippedInventoryItems.add(sword);
        return sword;
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        Pair<Integer, Integer> pos0 = orderedPath.get(0);
        character.moveDownPath();
        if (character.getX() == pos0.getValue0() && character.getY() == pos0.getValue1()) {
            cycle.set(cycle.get()+1);
        }
        moveBasicEnemies();
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
        Item i = (Item) item;
        makeUpReward(i);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    private void shiftSoldiersDownFromXCoordinate(int x){
        for (Soldier c: character.getArmy()){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (Enemy e: enemies){
            e.move(this);
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition(){
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(3); 
        if ((choice == 0) && (enemies.size() < this.getMaxNumTotal().get())){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    private Pair<Integer, Integer> possiblyGetDoggieSpawnPosition(){
        // if (cycle.get() < 20) {
        //     return null; 
        // }
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(10); 
        if ((choice == 0) && (enemies.size() < this.getMaxNumTotal().get())){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    private Pair<Integer, Integer> possiblyGetElanSpawnPosition(){
        // if (cycle.get() < 40 || character.getExp() < 10000) {
        //     return null; 
        // }
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(20); 
        if ((choice == 0) && (enemies.size() < this.getMaxNumTotal().get())){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * similar as possible position of an enemy spawn
     * @return
     */
    public Pair<Integer, Integer> possiblyGetGoldPosition(){
        
        // has a chance spawning an item on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); 
        if ((choice == 0) && (spawnItems.size() < 4)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * similar as possible position of an enemy spawn
     * @return
     */
    public Pair<Integer, Integer> possiblyGetPosionPosition(){
        
        // has a chance spawning an item on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); 
        if ((choice == 0) && (spawnItems.size() < 4)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }
        if(card instanceof VampireCastleCard){
            if (!isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))) {
                VampireCastle newBuilding = new VampireCastle(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
        }
        else if(card instanceof ZombiePitCard){
            if (!isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY)) && 
                    ifNearPathTile(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))) {
                ZombiePit newBuilding = new ZombiePit(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
            
        }
        else if(card instanceof TowerCard){
            if (!isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY)) && 
                   ifNearPathTile(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))) { 
                Tower newBuilding = new Tower(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
        }
        else if(card instanceof VillageCard){
            if (isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))) {
                Village newBuilding = new Village(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
        }
        else if(card instanceof BarrackCard){
            if (isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))) {
                Barrack newBuilding = new Barrack(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
        }
        else if(card instanceof TrapCard){
            if (isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))) {
                Trap newBuilding = new Trap(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
        }
        else if(card instanceof CampFireCard){
            if (!isInPath(new Pair<Integer,Integer>(buildingNodeX, buildingNodeY))){
                CampFire newBuilding = new CampFire(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            }
        }
        
        return null;
    }

    /**
     * getter
     * @return
     */
    public Character getCharacter(){
        return this.character;
    }

    /**
     * getter
     * @return
     */
    public List<Pair<Integer, Integer>> getOrderedPath(){
        return this.orderedPath;
    }

    /*
    *remove building entities
    */

    public void removeBuildingEntities(Building building){
        building.destroy();
        buildingEntities.remove(building);
    }

    // public JSONObject<Item> getStoreItems(){
    //     JSONObject a = new JSONObject();
    //     JSONArray b = new JSONArray();
    //     for(Item i: items){
    //         if(!i.equals(TheOneRing)){
    //             JSONObject c = new JSONObject();
    //             //need getType();
    //             c.put("Name", i.getType());
    //             c.put("Gold", i.getValueInGold());
    //             b.put(c);
    //         }
    //     }
    //     a.put("Items", b);
    //     return a;
    // }

    /**
     * to check if a pair of position coordinates in the path tiles
     * @param p
     * @return
     */
    public boolean isInPath(Pair<Integer, Integer> p) {
        for (Pair<Integer, Integer> temp: orderedPath) {
            if (temp.getValue0().equals(p.getValue0()) && temp.getValue1().equals(p.getValue1())) {
                return true;
            }
        }
        return false;
    }

    /**
     * to check if a pair of position coordinates next to the path tiles
     * @param p
     * @return
     */
    public boolean ifNearPathTile(Pair<Integer, Integer> p){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int x = p.getValue0() + i;
                int y = p.getValue1() + j;
                Pair<Integer, Integer> a = new Pair<Integer, Integer>(x, y);
                if(isInPath(a)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * obtain gold
     * @param gold
     */
    public void addGold(int gold) {
        character.addGold(gold);
    }

    /**
     * obtain exp
     * @param exp
     */
    public void addExp(int exp) {
        character.addExp(exp);
    }

    /**
     * to pick up a potion and add it to the list
     * @return
     */
    public List<Potion> pickUp() {
        List<Potion> picked = new ArrayList<>();
        List<Item> copied = new ArrayList<>();
        for (Item temp: spawnItems) {
            copied.add(temp);
        }
        for (Item temp: copied) {
            if (temp.getX() == character.getX() && temp.getY() == character.getY()) {
                if (temp instanceof Gold) {
                    addGold(temp.getValueInGold());
                    temp.destroy();
                    spawnItems.remove(temp);
                }
                else {

                    temp.destroy();

                    Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
                    if (firstAvailableSlot == null){
                        // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
                        removeItemByPositionInUnequippedInventoryItems(0);
                        firstAvailableSlot = getFirstAvailableSlotForItem();
                    }
        
                    // now we insert the new sword, as we know we have at least made a slot available...
                    Potion po = new Potion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
                    unequippedInventoryItems.add(po);
                    picked.add(po);
                    spawnItems.remove(temp);
                }
            }
        }
        return picked;
    }

    /**
     * to create a hero castle
     * @return
     */
    public HeroCastle getHeroCastle() {
        Pair<Integer, Integer> pos = orderedPath.get(0);
        SimpleIntegerProperty x = new SimpleIntegerProperty(pos.getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(pos.getValue1());

        HeroCastle a = new HeroCastle(x, y); 
        buildingEntities.add(a);

        return a;
    }

    /**
     * get some reward after the item is destroyed
     * @param i
     */
    public void makeUpReward(Item i) {
        character.addGold((int)(i.getValueInGold()*0.1));
    }

    /**
     * to equip an inventory
     * @param nodeX
     * @param nodeY
     * @return
     */
    public Equipment covertEquippedToEquipped(int nodeX, int nodeY) {
        Equipment item = null;
        for (Entity c: unequippedInventoryItems){
            if ((c.getX() == nodeX) && (c.getY() == nodeY) && c instanceof Equipment){
                item = (Equipment) c;
                break;
            }
        }

        if (item instanceof Sword) {
            Weapon old = equippedItems.getWeapon();
            if (old != null) {
                old.destroy();
                equippedItems.dropWeapon();
            }
            Sword newWeapon = new Sword(new SimpleIntegerProperty(weaponSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipWeapon(newWeapon);
            removeUnequippedInventoryItem(item);
            return newWeapon;
        }

        else if (item instanceof Stake) {
            Weapon old = equippedItems.getWeapon();
            if (old != null) {
                old.destroy();
                equippedItems.dropWeapon();
            }
            Stake newWeapon = new Stake(new SimpleIntegerProperty(weaponSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipWeapon(newWeapon);
            removeUnequippedInventoryItem(item);
            return newWeapon;
        }

        else if (item instanceof Staff) {
            Weapon old = equippedItems.getWeapon();
            if (old != null) {
                old.destroy();
                equippedItems.dropWeapon();
            }
            Staff newWeapon = new Staff(new SimpleIntegerProperty(weaponSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipWeapon(newWeapon);
            removeUnequippedInventoryItem(item);
            return newWeapon;
        }

        else if (item instanceof Helmet) {
            Helmet old = equippedItems.getHelmet();
            if (old != null) {
                old.destroy();
                equippedItems.dropHelmet();
            }
            Helmet newHelmet = new Helmet(new SimpleIntegerProperty(helmetSlot), new SimpleIntegerProperty(helmetHeight));
            equippedItems.equipHelmet(newHelmet);
            removeUnequippedInventoryItem(item);
            return newHelmet;
        }

        else if (item instanceof Armour) {
            Armour old = equippedItems.getArmour();
            if (old != null) {
                old.destroy();
                equippedItems.dropArmour();
            }
            Armour newArmour = new Armour(new SimpleIntegerProperty(armourSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipArmour(newArmour);
            removeUnequippedInventoryItem(item);
            return newArmour;
        }
        else if (item instanceof TreeStump) {
            Shield old = equippedItems.getShield();
            if (old != null) {
                old.destroy();
                equippedItems.dropShield();
            }
            TreeStump newShield = new TreeStump(new SimpleIntegerProperty(shieldSlot), new SimpleIntegerProperty(equippedHeight), isConfusing());
            equippedItems.equipShield(newShield);
            removeUnequippedInventoryItem(item);
            return newShield;
        }
        else if (item instanceof Shield) {
            Shield old = equippedItems.getShield();
            if (old != null) {
                old.destroy();
                equippedItems.dropShield();
            }
            Shield newShield = new Shield(new SimpleIntegerProperty(shieldSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipShield(newShield);
            removeUnequippedInventoryItem(item);
            return newShield;
        }

        else if (item instanceof Anduril) {
            Weapon old = equippedItems.getWeapon();
            if (old != null) {
                old.destroy();
                equippedItems.dropWeapon();
            }
            Anduril newWeapon = new Anduril(new SimpleIntegerProperty(weaponSlot), new SimpleIntegerProperty(equippedHeight), isConfusing());
            equippedItems.equipWeapon(newWeapon);
            removeUnequippedInventoryItem(item);
            return newWeapon;
        }
        return null;

    }

    /**
     * prevent concurrency exception
     * @param nodeX
     * @param nodeY
     */
    public void consumePotion(int nodeX, int nodeY) {
        if (battleLock.get() == 0) {
            return;
        }
        battleLock.set(0);
        List<Entity> copied = new ArrayList<>();
        for (Entity e: unequippedInventoryItems) {
            copied.add(e);
        }
        for (Entity e: copied) {
            if (e.getX() == nodeX && e.getY() == nodeY && e instanceof Potion) {
                Potion a = (Potion) e;
                a.recoverHealth(character);
                removeUnequippedInventoryItem(e);
            }
        }
        battleLock.set(1);
    }

    /**
     * prevent concurrency exception
     */
    public void consumePotion() {
        if (battleLock.get() == 0) {
            return;
        }
        battleLock.set(0);
        List<Entity> copied = new ArrayList<>();
        for (Entity e: unequippedInventoryItems) {
            copied.add(e);
        }
        for (Entity e: copied) {
            if (e instanceof Potion) {
                Potion a = (Potion) e;
                a.recoverHealth(character);
                removeUnequippedInventoryItem(e);
                break;
            }
        }
        battleLock.set(1);
    }

    /**
     * getter
     * @return
     */
    public Equipped getEquip() {
        return equippedItems;
    }

    /**
     * check if the goal of the game is achieved
     * @return
     */
    public boolean checkGoal() {
        return goal.checkGoal(this);
    }

    /**
     * getter
     * @return
     */
    public List<Item> getBoughtItem() {
        return boughtItems;
    }


    /**
     * check if the character is at the hero castle
     * @return
     */
    public boolean atHeroCastle() {
        Pair<Integer, Integer> pos = orderedPath.get(0);
        if (character.getX() == pos.getValue0() && character.getY() == pos.getValue1()){
            return true;
        }
        return false;
    }

    public List<Item> getInventory() {
        return unequippedInventoryItems;
    }

    public boolean isAnduril() {
        return andurilExist;
    }

    public boolean isTreeStump() {
        return treeStumpExist;
    }

    public void restart() {
        doggiePrice.set(5000);
        battleLock.set(1);
        theOneRingExist = false;
        andurilExist = false;
        treeStumpExist = false;
        defeatedBoss.set(0);
        
        for (Enemy i: enemies){
            i.destroy();
        }
        enemies = new ArrayList<>();

        for (Card i: cardEntities){
            i.destroy();
        }
        cardEntities = new ArrayList<>();

        for (Item i: unequippedInventoryItems){
            i.destroy();
        }
        unequippedInventoryItems = new ArrayList<>();

        for (Building i: buildingEntities){
            if (i instanceof HeroCastle) {
                continue;
            }
            i.destroy();
        }
        buildingEntities = new ArrayList<>();

        for (Item i: spawnItems){
            i.destroy();
        }
        spawnItems = new ArrayList<>();

        cycle.set(1);

        equippedItems.dropArmour();
        equippedItems.dropHelmet();
        equippedItems.dropRing();
        equippedItems.dropShield();
        equippedItems.dropWeapon();

        character.initialize();
    }

    public SimpleIntegerProperty getDefeatedBoss() {
        return defeatedBoss;
    }

    public void defeatBoss() {
        defeatedBoss.set(defeatedBoss.get()+1);
    }

    public boolean isConfusing() {
        return mode == CONFUSING;
    }
    //write and read JSON files for saving games
    public void writeToJSON () {
        try {
            FileWriter writer = new FileWriter("archive/" + LocalDateTime.now().toString() + ".json");
            JSONObject character = this.character.characterToJson();
            JSONArray enemies = new JSONArray();
            for (Enemy e: this.getEnemies()) {
               enemies.put(e.toJSON()); 
            }
            JSONArray cards = new JSONArray();
            for (Card c: this.cardEntities) {
                cards.put(c.toJSON());
            }
            JSONArray buildings = new JSONArray();
            for (Building b: this.buildingEntities) {
                buildings.put(b.toJSON());
            }
            JSONArray spawnItems = new JSONArray();
            for (Item i: this.spawnItems) {
                spawnItems.put(i.toJSON());
            }
            JSONArray bag = new JSONArray();
            for (Item i: this.unequippedInventoryItems) {
                bag.put(i.toJSON());
            }
            JSONObject equippedItems = this.equippedItems.toJSON();
            JSONObject cycle = new JSONObject();
            cycle.put("cycle", this.cycle.get());
            JSONObject goal = new JSONObject();
            goal.put("goal", this.goal.toJSON());
            JSONObject doggie = new JSONObject();
            doggie.put("doggiePrice", this.doggiePrice.get());
            JSONObject world = new JSONObject();
            
            world.put("mode", mode);
            world.put("defeats", getDefeatedBoss().get());
            world.put("character", character);
            world.put("enemies", enemies);
            world.put("cards", cards);
            world.put("buildings", buildings);
            world.put("spawnitems", spawnItems);
            world.put("equippeditems", equippedItems);
            world.put("cycle", cycle);
            world.put("goal", goal);
            world.put("doggie", doggie);
            world.put("theonering", theOneRingExist);
            world.put("anduril", andurilExist);
            world.put("treestump", treeStumpExist);
            world.put("height", getHeight());
            world.put("width", getWidth());
            world.put("path", pathOfMap);
            world.put("bag", bag);
            writer.write(world.toString());
            writer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    

    //TODO:
    public void readFromJSON(JSONObject json) {
        reloadCharacter(json);
        reloadCard(json);
        this.doggiePrice.set(json.getJSONObject("doggie").getInt("doggiePrice"));
        reloadBuilding(json);
        reloadSpawnItem(json);
        reloadEquippedItem(json);
        reloadEnemies(json);
        reloadGoal(json);
        reloadBag(json);
        this.cycle.set(json.getJSONObject("cycle").getInt("cycle"));
        this.theOneRingExist = json.getBoolean("theonering");
        this.andurilExist = json.getBoolean("anduril");
        this.treeStumpExist = json.getBoolean("treestump");
        this.defeatedBoss.set(json.getInt("defeats"));
        this.mode = json.getInt("mode");
    }

    public void reloadBag(JSONObject json) {
        JSONArray bag = json.getJSONArray("bag");
        for (int i = 0; i < bag.length(); i++) {
            JSONObject item = bag.getJSONObject(i);
            SimpleIntegerProperty x = new SimpleIntegerProperty(item.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(item.getInt("y"));
            switch (item.getString("type")) {
                case "Potion":
                    Item i0 = new Potion(x, y);
                    unequippedInventoryItems.add(i0);
                    break;
                case "Sword":
                    Item i1 = new Sword(x, y);
                    unequippedInventoryItems.add(i1);
                    break;
                case "Shield":
                    Item i2 = new Shield(x, y);
                    unequippedInventoryItems.add(i2);
                    break;
                case "Armour":
                    Item i3 = new Armour(x, y);
                    unequippedInventoryItems.add(i3);
                    break;
                case "Helmet":
                    Item i4 = new Helmet(x, y);
                    unequippedInventoryItems.add(i4);
                    break;
                case "Anduril":
                    Item i5 = new Anduril(x, y, isConfusing());
                    unequippedInventoryItems.add(i5);
                    break;
                case "TreeStump":
                    Item i6 = new TreeStump(x, y, isConfusing());
                    unequippedInventoryItems.add(i6);
                    break;
                case "DoggieCoin":
                    Item i7 = new DoggieCoin(x, y, this.doggiePrice);
                    unequippedInventoryItems.add(i7);
                    break;
                case "Staff":
                    Item i8 = new Staff(x, y);
                    unequippedInventoryItems.add(i8);
                    break;
                case "Stake":
                    Item i9 = new Stake(x, y);
                    unequippedInventoryItems.add(i9);
                    break;
                default:
                    break;
            }
        }
    }

    public void reloadEnemies(JSONObject json) {
        JSONArray enemies = json.getJSONArray("enemies");
        for (int i = 0; i < enemies.length(); i++) {
            JSONObject enemy = enemies.getJSONObject(i);
            int x = enemy.getInt("position");
            int lv = enemy.getInt("lv");
            PathPosition position = new PathPosition(x, orderedPath);
            switch (enemy.getString("type")) {
                case "Slug":
                    Enemy e = new Slug(position, lv);
                    this.enemies.add(e);
                    break;
                case "Zombie":
                    Enemy en = new Zombie(position, lv);
                    this.enemies.add(en);
                    break;
                case "Vampire":
                    Enemy ene = new Vampire(position, lv);
                    this.enemies.add(ene);
                    break;
                case "Doggie":
                    Enemy enem = new Doggie(position, lv);
                    this.enemies.add(enem);
                    break;
                case "Elan":
                    Enemy enemys = new ElanMuske(position, lv);
                    this.enemies.add(enemys);
                    break;
                default:
                    break;
            }
        }
    }

    public void reloadGoal(JSONObject json) {
        JSONObject goal = json.getJSONObject("goal");
        goal = goal.getJSONObject("goal");
        int goldGoal = goal.getInt("goldgoal");
        int expGoal = goal.getInt("expgoal");
        int cyclegoal = goal.getInt("cyclegoal");
        int bossgoal = goal.getInt("bossgoal");
        this.goal.setCycleGoal(cyclegoal);
        this.goal.setExpGoal(expGoal);
        this.goal.setGoldGoal(goldGoal);
        this.goal.setBossGoal(bossgoal);
    }

    public void reloadEquippedItem(JSONObject json) {
        JSONObject items = json.getJSONObject("equippeditems");
        JSONObject armour = items.getJSONObject("armour");
        JSONObject helmet = items.getJSONObject("helmet");
        JSONObject weapon = items.getJSONObject("weapon");
        JSONObject shield = items.getJSONObject("shield");
        JSONObject ring = items.getJSONObject("ring");
        if (!armour.isEmpty()) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(armour.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(armour.getInt("y"));
            int level = armour.getInt("level");
            Armour a = new Armour(x, y);
            for (int i = 0; i < level - 1; i++) {
                a.levelUp();
            }
            equippedItems.equipArmour(a);
        }
        if (!helmet.isEmpty()) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(helmet.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(helmet.getInt("y"));
            int level = helmet.getInt("level");
            Helmet a = new Helmet(x, y);
            for (int i = 0; i < level - 1; i++) {
                a.levelUp();
            }
            equippedItems.equipHelmet(a);
        }
        if (!weapon.isEmpty()) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(weapon.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(weapon.getInt("y"));
            int level = weapon.getInt("level");
            switch (weapon.getString("type")) {
                case "Anduril":
                    Anduril anduril = new Anduril(x, y, isConfusing());
                    int rareType = shield.getInt("raretype");
                    if (rareType == Item.DEFENCE) {
                        anduril.setSe((LoopManiaWorld world, Enemy e)->EffectFactory.treeStump(world, e));
                        anduril.setSecondValue(EffectFactory.treeStump);
                        rareType = Item.DEFENCE;
                    }
                    else if (rareType == Item.HEALTH) {
                        anduril.setSe((LoopManiaWorld world, Enemy e)->EffectFactory.theOneRing(world));
                        rareType = Item.HEALTH;
                    }
                    for (int i = 0; i < level - 1; i++) {
                        anduril.levelUp();
                    }
                    equippedItems.equipWeapon((Weapon) anduril);
                    break;
                case "Sword":
                    Weapon sword = new Sword(x, y);
                    for (int i = 0; i < level - 1; i++) {
                        sword.levelUp();
                    }
                    equippedItems.equipWeapon(sword);
                    break;
                case "Staff":
                    Weapon staff = new Staff(x, y);
                    for (int i = 0; i < level - 1; i++) {
                        staff.levelUp();
                    }
                    equippedItems.equipWeapon(staff);
                    break;
                case "Stake":
                    Weapon stake = new Stake(x, y);
                    for (int i = 0; i < level - 1; i++) {
                        stake.levelUp();
                    }
                    equippedItems.equipWeapon(stake);
                    break;
                default:
                    break;
            }
            
        }
        if (!shield.isEmpty()) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(shield.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(shield.getInt("y"));
            int level = shield.getInt("level");
            if (shield.getString("type").equals("TreeStump")) {
                TreeStump tree = new TreeStump(x, y, isConfusing());
                int rareType = shield.getInt("raretype");
                if (rareType == Item.ATTACK) {
                    tree.setSe((LoopManiaWorld world, Enemy e)->EffectFactory.anduril(world, e));
                    tree.setSecondValue(EffectFactory.anduril);
                    rareType = Item.ATTACK;
                }
                else if (rareType == Item.HEALTH) {
                    tree.setSe((LoopManiaWorld world, Enemy e)->EffectFactory.theOneRing(world));
                    rareType = Item.HEALTH;
                }
                for (int i = 0; i < level - 1; i++) {
                    tree.levelUp();
                }
                equippedItems.equipShield((Shield) tree);
            } else {
                Shield a = new Shield(x, y);
                for (int i = 0; i < level - 1; i++) {
                    a.levelUp();
                }
                equippedItems.equipShield(a);
            }
            
            
        }
        if (!ring.isEmpty()) {
            SimpleIntegerProperty x = new SimpleIntegerProperty(ring.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(ring.getInt("y"));
            int rareType = ring.getInt("raretype");
            TheOneRing a = new TheOneRing(x, y, isConfusing());
            if (rareType == 1) {
                a.setSe((LoopManiaWorld world, Enemy e)->EffectFactory.anduril(world, e));
                a.setSecondValue(EffectFactory.anduril);
            } else if (rareType == 2) {
                a.setSe((LoopManiaWorld world, Enemy e)->EffectFactory.treeStump(world, e));
                a.setSecondValue(EffectFactory.treeStump);
            }
            a.setRareType(rareType);
            
            equippedItems.equipRing(a);
        }
    }

    public void reloadSpawnItem(JSONObject json) {
        JSONArray items = json.getJSONArray("spawnitems");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            SimpleIntegerProperty x = new SimpleIntegerProperty(item.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(item.getInt("y"));
            
            switch (item.getString("type")) {
                case "Potion":
                    Item potion = new Potion(x, y);
                    spawnItems.add(potion);
                    break;
                case "Gold":
                    int value = item.getInt("valueingold");
                    Item gold = new Gold(x, y, value);
                    spawnItems.add(gold);
                    break;
                default:
                    break;
            }
        }
    }

    public void reloadBuilding(JSONObject json) {
        JSONArray buildings = json.getJSONArray("buildings");

        for (int i = 1; i < buildings.length(); i++) {
            JSONObject building = buildings.getJSONObject(i);
            SimpleIntegerProperty x = new SimpleIntegerProperty(building.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(building.getInt("y"));

            switch (building.getString("type")) {
                case "Barrack":
                    Building b = new Barrack(x, y);
                    this.buildingEntities.add(b);
                    break;
                case "Campfire":
                    Building c = new CampFire(x, y);
                    this.buildingEntities.add(c);
                    break;
                case "Tower":
                    Building d = new Tower(x, y);
                    this.buildingEntities.add(d);
                    break;
                case "Trap":
                    Building e = new Trap(x, y);
                    this.buildingEntities.add(e);
                    break;
                case "VampireCastle":
                    Building v = new VampireCastle(x, y);
                    this.buildingEntities.add(v);
                    break;
                case "Village":
                    Building f = new Village(x, y);
                    this.buildingEntities.add(f);
                    break;
                case "ZombiePit":
                    Building z = new ZombiePit(x, y);
                    this.buildingEntities.add(z);
                    break;
                default:
                    break;
            
            }
        }
    }

    public void reloadCard(JSONObject json) {
        JSONArray cards = json.getJSONArray("cards");
        
        for (int i = 0; i < cards.length(); i++) {
            JSONObject card = cards.getJSONObject(i);
            SimpleIntegerProperty x = new SimpleIntegerProperty(card.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(card.getInt("y"));

            switch (card.getString("type")) {
                case "BarrackCard":
                    Card b = new BarrackCard(x, y);
                    this.cardEntities.add(b);
                    break;
                case "CampfireCard":
                    Card c = new CampFireCard(x, y);
                    this.cardEntities.add(c);
                    break;
                case "TowerCard":
                    Card d = new TowerCard(x, y);
                    this.cardEntities.add(d);
                    break;
                case "TrapCard":
                    Card e = new TrapCard(x, y);
                    this.cardEntities.add(e);
                    break;
                case "VampireCard":
                    Card v = new VampireCastleCard(x, y);
                    this.cardEntities.add(v);
                    break;
                case "VillageCard":
                    Card f = new VillageCard(x, y);
                    this.cardEntities.add(f);
                    break;
                case "ZombieCard":
                    Card z = new ZombiePitCard(x, y);
                    this.cardEntities.add(z);
                    break;
                default:
                    break;
            }
        }
    }

    public void reloadCharacter(JSONObject jsonn) {
        JSONObject json = jsonn.getJSONObject("character");
        int gold = json.getInt("gold");
        int curHealth = json.getInt("curHealth");
        int exp = json.getInt("exp"); 
        int curPosition = json.getInt("position");
        List<Soldier> army = reloadSoldier(json, this.character);
        this.character.getG().set(gold);
        this.character.setArmy(army);
        this.character.getExperience().set(exp);
        this.character.getAttr().getCurHealth().set(curHealth);
        this.character.getPosition().setCurrentPositionInPath(curPosition);
        this.character.shouldExist().set(true);
        int att = json.getInt("attpoints");
        int def = json.getInt("defpoints");
        int health = json.getInt("healthpoints");
        character.getAttackPoints().set(att);
        if (att > 10) {
            character.setRage();
        }
        character.getDefencePoints().set(def);
        if (def > 10) {
            character.setMiss();
        }
        character.getHealthPoints().set(health);
    }

    public List<Soldier> reloadSoldier(JSONObject json, Character ch) {
        JSONArray army = json.getJSONArray("army");
        List<Soldier>  soldiers = new ArrayList<>();
        for (int i = 0; i < army.length(); i++) {
            JSONObject soldier = army.getJSONObject(i);
            SimpleIntegerProperty x = new SimpleIntegerProperty(soldier.getInt("x"));
            SimpleIntegerProperty y = new SimpleIntegerProperty(soldier.getInt("y"));
            EntityAttribute attr = reloadAttr(soldier.getJSONObject("attr"));
            Soldier s = new Soldier(x, y, ch);
            s.setAttr(attr);
            soldiers.add(s);
        }
        return soldiers;
        
    }

    public EntityAttribute reloadAttr(JSONObject json) {
        EntityAttribute attr = new EntityAttribute(json.getInt("attack"), json.getInt("defence"), json.getInt("health"));
        attr.getCurHealth().set(json.getInt("curHealth"));
        return attr;
    }

}
