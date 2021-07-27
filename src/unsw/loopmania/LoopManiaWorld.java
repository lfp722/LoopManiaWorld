package unsw.loopmania;
import unsw.loopmania.goal.FinalGoal;
import unsw.loopmania.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Item;
import unsw.loopmania.items.Potion;
import unsw.loopmania.items.Sword;


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

    private int doggiePrice;

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

    private List<Item> soldItems = new ArrayList<>();

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

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

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private FinalGoal goal;

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
        nonSpecifiedEntities = new ArrayList<>();
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
        doggiePrice = 100;
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

    

    public int getDoggiePrice() {
        return doggiePrice;
    }

    public void setDoggiePrice(int doggiePrice) {
        this.doggiePrice = doggiePrice;
    }

    public TheOneRing addEquippedRing() {
        if (equippedItems.getRing() != null) {
            equippedItems.getRing().destroy();
        }
        TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(ringWidth), new SimpleIntegerProperty(ringHeight));
        equippedItems.equipRing(ring);
        return ring;
    }

    public SimpleIntegerProperty getCycle() {
        return cycle;
    }

    public SimpleIntegerProperty getMaxNumTotal() {
        return maxNumTotal;
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
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<Slug> possiblySpawnEnemies(){
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<Slug> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Slug enemy = new Slug(new PathPosition(indexInPath, orderedPath), cycle.get());
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
            Doggie enemy = new Doggie(new PathPosition(indexInPath, orderedPath), cycle.get());
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
            ElanMuske enemy = new ElanMuske(new PathPosition(indexInPath, orderedPath), cycle.get());
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
            nonSpecifiedEntities.add(i);
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
            nonSpecifiedEntities.add(i);
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
                    //System.out.println("Enemy attack character");
                }
                else{
                    Soldier brave = ch.getArmy().get(0);
                    e.attack(brave, this);
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

        battle(battleEnemies, defeatedEnemies, character);
        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
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
        Anduril anduril = new Anduril(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
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
        TreeStump ts = new TreeStump(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
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
        DoggieCoin sword = new DoggieCoin(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
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
        if ((choice == 0) && (enemies.size() < 5)){
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
        if (cycle.get() < 20) {
            return null; 
        }
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(10); 
        if ((choice == 0) && (enemies.size() < 5)){
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
        if (cycle.get() < 40 || character.getExp() < 10000) {
            return null; 
        }
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(20); 
        if ((choice == 0) && (enemies.size() < 5)){
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
        if ((choice == 0) && (nonSpecifiedEntities.size() < 4)){
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
        if ((choice == 0) && (nonSpecifiedEntities.size() < 4)){
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
            Anduril newWeapon = new Anduril(new SimpleIntegerProperty(weaponSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipWeapon(newWeapon);
            removeUnequippedInventoryItem(item);
            return newWeapon;
        }

        else if (item instanceof TreeStump) {
            Shield old = equippedItems.getShield();
            if (old != null) {
                old.destroy();
                equippedItems.dropShield();
            }
            TreeStump newShield = new TreeStump(new SimpleIntegerProperty(shieldSlot), new SimpleIntegerProperty(equippedHeight));
            equippedItems.equipShield(newShield);
            removeUnequippedInventoryItem(item);
            return newShield;
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

    public List<Item> getSoldItem() {
        return soldItems;
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

    
}
