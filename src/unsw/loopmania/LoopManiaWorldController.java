package unsw.loopmania;

import unsw.loopmania.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.EnumMap;

import java.io.File;
import java.io.IOException;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.scene.shape.Rectangle;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ZOMBIECARD,
    VAMPIRECARD,
    VILLAGECARD,
    TOWERCARD,
    TRAPCARD,
    BARRACKCARD,
    CAMPFIRECARD,
    ITEM,
    HELMET,
    SHIELD,
    ARMOUR,
    SWORD,
    STAKE,
    STAFF,
    POTION,
    THEONERING,
    ANDURIL,
    TREESTUMP, 
    DOGGIECOIN
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    @FXML
    private Label maxHealth;

    @FXML
    private Label curHealth;

    @FXML
    private Label attack;

    @FXML
    private Label defence;

    @FXML
    private Label gold;

    @FXML
    private Label level;

    @FXML
    private Label experience;

    @FXML
    private Label cycle;

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    /**
     * soldier
     */
    @FXML
    private GridPane soldiers;

    @FXML
    private GridPane attributeBar;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    private Image vampireCastleCardImage;
    private Image towerCardImage;
    private Image villageCardImage;
    private Image zombiePitCardImage;
    private Image trapCardImage;
    private Image barrackCardImage;
    private Image campfireCardImage;
    private Image swordImage;
    private Image slugImage;
    private Image zombieImage;
    private Image vampireImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image stakeImage;
    private Image staffImage;
    private Image goldImage;
    private Image potionImage;
    private Image vampireCastleBuildingImage;
    private Image herosCastleImage;
    private Image towerImage;
    private Image villageImage;
    private Image zombiePitImage;
    private Image trapImage;
    private Image barrackImage;
    private Image campfireImage;
    private Image soldierImage;
    private Image theOneRingImage;
    private Image doggieImage;
    private Image elanImage;
    private Image doggieCoinImage;
    private Image andurilImage;
    private Image treeStumpImage;


    private Text expLabel;

    private Rectangle healthBar;

    private Text goldText;

    private Text expText;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the building/item which should be dropped
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    private StoreSwitcher storeSwitcher;

    private MenuSwitcher winSwitcher;

    private MenuSwitcher loseSwitcher;

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        entityImages = new ArrayList<>(initialEntities);
        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        towerCardImage = new Image((new File("src/images/tower_card.png")).toURI().toString());
        villageCardImage = new Image((new File("src/images/village_card.png")).toURI().toString());
        zombiePitCardImage = new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());
        trapCardImage = new Image((new File("src/images/trap_card.png")).toURI().toString());
        barrackCardImage = new Image((new File("src/images/barracks_card.png")).toURI().toString());
        campfireCardImage = new Image((new File("src/images/campfire_card.png")).toURI().toString());
        slugImage = new Image((new File("src/images/slug.png")).toURI().toString());
        zombieImage = new Image((new File("src/images/zombie.png")).toURI().toString());
        vampireImage = new Image((new File("src/images/vampire.png")).toURI().toString());
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        goldImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        potionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        vampireCastleBuildingImage = new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        herosCastleImage = new Image((new File("src/images/heros_castle.png")).toURI().toString());
        towerImage = new Image((new File("src/images/tower.png")).toURI().toString());
        villageImage = new Image((new File("src/images/village.png")).toURI().toString());
        zombiePitImage = new Image((new File("src/images/zombie_pit.png")).toURI().toString());
        trapImage = new Image((new File("src/images/trap.png")).toURI().toString());
        barrackImage = new Image((new File("src/images/barracks.png")).toURI().toString());
        campfireImage = new Image((new File("src/images/campfire.png")).toURI().toString());
        soldierImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());
        theOneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        doggieImage = new Image((new File("src/images/doggie.png")).toURI().toString());
        elanImage = new Image((new File("src/images/ElanMuske.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {
        
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);
        setLabel();
        onLoad(world.getHeroCastle());
    }

    /**
     * set basic vision of the frontend stats
     */
    public void setLabel() {
        //String h = Integer.toString(world.getCharacter().getAttr().getCurHealth().get()).concat("/").concat(Integer.toString(world.getCharacter().getAttr().getHealth().get()));
        //health.setText(h);

        expLabel = new Text();
        expLabel.setText("XP");
        expLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        expLabel.setFill(Color.PURPLE);
        attributeBar.add(expLabel,0,2);

        healthBar = new Rectangle(70, 30, Color.RED);
        Rectangle bottleBar = new Rectangle(70, 30, Color.BLACK);
        DoubleProperty healthPer = new SimpleDoubleProperty();
        IntegerProperty curH = world.getCharacter().getAttr().getCurHealth();
        IntegerProperty maxH = world.getCharacter().getAttr().getHealth();
        healthPer.bind(Bindings.createDoubleBinding(()->(double)curH.get()/(double)maxH.get(), curH, maxH));
        DoubleBinding b = healthPer.multiply(70);
        healthBar.widthProperty().bind(b);
        attributeBar.add(bottleBar,1,0);
        attributeBar.add(healthBar,1,0);

        goldText = new Text();
        StringBinding g = world.getCharacter().getG().asString();
        goldText.textProperty().bind(g);
        goldText.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        goldText.setFill(Color.GREEN);
        attributeBar.add(goldText,1,1);

        expText = new Text();
        IntegerProperty curExp = world.getCharacter().getExperience();
        IntegerProperty nextExp = world.getCharacter().getNextLvExp();
        StringBinding e = Bindings.createStringBinding(()->curExp.asString().get().concat("/").concat(nextExp.asString().get()), curExp, nextExp);
        expText.textProperty().bind(e);
        expText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        expText.setFill(Color.PURPLE);
        attributeBar.add(expText,1,2);


        Text attackLabel = new Text();
        attackLabel.setText("ATT");
        attackLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        attackLabel.setFill(Color.RED);
        attributeBar.add(attackLabel,0,3);

        Text attackText = new Text();
        IntegerProperty iA = world.getCharacter().getAttr().getAttack();
        IntegerProperty wA = world.getEquip().getDamage();
        StringBinding a = Bindings.createStringBinding(()->iA.asString().get().concat("(+").concat(wA.asString().get()).concat(")"), curExp, nextExp);
        attackText.textProperty().bind(a);
        attackText.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        attackText.setFill(Color.RED);
        attributeBar.add(attackText,1,3);

        Text defenceLabel = new Text();
        defenceLabel.setText("DEF");
        defenceLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        defenceLabel.setFill(Color.BLUE);
        attributeBar.add(defenceLabel,0,4);

        Text defenceText = new Text();
        StringBinding d = world.getCharacter().getEquip().getDefence().asString();
        defenceText.textProperty().bind(d);
        defenceText.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        defenceText.setFill(Color.BLUE);
        attributeBar.add(defenceText,1,4);

        Text cycleLabel = new Text();
        cycleLabel.setText("Cyc");
        cycleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        cycleLabel.setFill(Color.BLACK);
        attributeBar.add(cycleLabel,0,5);

        Text cycleText = new Text();
        StringBinding c = world.getCycle().asString();
        cycleText.textProperty().bind(c);
        cycleText.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        cycleText.setFill(Color.BLACK);
        attributeBar.add(cycleText,1,5);


        Text dcText = new Text();
        StringBinding dc = world.getDoggiePrice().asString();
        dcText.textProperty().bind(dc);
        dcText.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        dcText.setFill(Color.ORANGE);
        attributeBar.add(dcText,1,6);

    }



    /**
     * create and run the timer
     */
    public void startTimer(){
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            //System.out.println(world.checkGoal());
            if (world.checkGoal()) {
                System.out.println("winning game");
                switchToWin();
            }
            world.runTickMoves();
            if (world.atHeroCastle()) {
                System.out.println("Switching to store");
                switchToStore();
            }
            if (!world.getBoughtItem().isEmpty()) {
                for (Item i: world.getBoughtItem()) {
                    onLoad(i);
                }
                world.getBoughtItem().clear();
            }
            List<Building> copied = new ArrayList<>();
            for (Building b: world.getBuildingEntities()) {
                copied.add(b);
            }
            for (Building b: copied) {
                if (b instanceof Barrack) {
                    Barrack a = (Barrack) b;
                    Soldier s = a.soldierProducer(world);
                    if(s != null) {
                        onLoad(s);
                    }
                } else {
                    b.specialEffect(world);
                }
                
            }
            List<Enemy> defeatedEnemies = world.runBattles();
            if (!world.getCharacter().shouldExist().get()) {
                if (world.getEquip().getRing() != null) {
                    world.getEquip().getRing().rebirth(world);
                    world.getEquip().getRing().destroy();
                    world.getEquip().dropRing();
                } else {
                    System.out.println("losing game");
                    switchToLose();
                }
                
            }
            List<Potion> picked = world.pickUp();
            for (Potion i: picked) {
                onLoad(i);
            }
            for (Enemy e: defeatedEnemies){
                reactToEnemyDefeat(e);
            }
            List<Slug> newEnemies = world.possiblySpawnEnemies();
            for (Slug newEnemy: newEnemies){
                onLoad(newEnemy);
            }
            List<Zombie> newZombies = world.possiblySpawnZombies();
            for (Zombie newEnemy: newZombies){
                onLoad(newEnemy);
            }
            List<Vampire> newVampires = world.possiblySpawnVampire();
            for (Vampire newEnemy: newVampires){
                onLoad(newEnemy);
            }
            List<Doggie> newDoggies = world.possiblySpawnDoggies();
            for (Doggie newEnemy: newDoggies){
                onLoad(newEnemy);
            }
            List<ElanMuske> newElans = world.possiblySpawnElans();
            for (ElanMuske newEnemy: newElans){
                onLoad(newEnemy);
            }
            
            List<Gold> newGolds = world.possiblySpawnGold();
            List<Potion> newPotions = world.possiblySpawnPotion();
            for (Gold newGold: newGolds) {
                onLoad(newGold);
            }
            for (Potion newPotion: newPotions) {
                onLoadPick(newPotion);
            }
            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate(){
        //pause();
        System.out.println("exiting");
        System.exit(0);
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a vampire card from the world, and pair it with an image in the GUI
     */
    private void loadVampireCard() {
        // TODO = load more types of card
        VampireCastleCard vampireCastleCard = world.loadVampireCard();
        onLoad(vampireCastleCard);
    }

    /**
     * load a sword from the world, and pair it with an image in the GUI
     */
    private void loadSword(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Sword sword = world.addUnequippedSword();
        onLoad(sword);
    }

    // private void loadStake(){
    //     // TODO = load more types of weapon
    //     // start by getting first available coordinates
    //     Stake sword = world.addUnequippedStake();
    //     onLoad(sword);
    // }

    // private void loadStaff(){
    //     // TODO = load more types of weapon
    //     // start by getting first available coordinates
    //     Staff sword = world.addUnequippedStaff();
    //     onLoad(sword);
    // }

    private void loadArmour(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Armour armour = world.addUnequippedArmour();
        onLoad(armour);
    }

    private void loadShield(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Shield shield = world.addUnequippedShield();
        onLoad(shield);
    }

    private void loadHelmet(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Helmet helmet = world.addUnequippedHelmet();
        onLoad(helmet);
    }

    private void loadPotion(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Potion potion = world.addPotion();
        onLoad(potion);
    }

    private void loadTheOneRing() {
        TheOneRing ring = world.addEquippedRing();
        onEquip(ring);
    }

    private void loadDoggieCoin(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        DoggieCoin potion = world.addDoggie();
        onLoad(potion);
    }

    private void loadAnduril(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Anduril helmet = world.addUnequippedAnduril();
        onLoad(helmet);
    }

    private void loadTreeStump(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        TreeStump helmet = world.addUnequippedTreeStump();
        onLoad(helmet);
    }

    

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    public void reactToEnemyDefeat(Enemy enemy) {
        if(enemy instanceof Slug) {
            loadGold(enemy.getGoldAfterDeath());
            loadExp(enemy.getExpAfterDeath());
            int choice = new Random().nextInt(100);
            if ((new Random()).nextInt(100) < 30) {
                loadCard();
            } 
            if (world.isTheOneRing() && choice < 30) {
                loadTheOneRing();
            }
            else if (world.isAnduril() && choice < 60) {
                loadAnduril();
            }
            else if (world.isTreeStump() && choice < 90) {
                loadTreeStump();
            }
        } else if (enemy instanceof Vampire) {
            loadGold(enemy.getGoldAfterDeath());
            loadExp(enemy.getExpAfterDeath());
            Random random = new Random();
            int choice = random.nextInt(1000);
            if (choice < 100) {
                loadSword();
            }
            else if (choice < 200) {
                loadArmour();
            }
            else if (choice < 300) {
                loadShield();
            }
            else if (choice < 400) {
                loadHelmet();
            }
            else if (choice < 500) {
                loadPotion();
            }
            else if (choice < 600) {
                loadCard();
            } 
            else if (world.isTheOneRing() && choice < 601) {
                loadTheOneRing();
            }
            else if (world.isAnduril() && choice < 602) {
                loadAnduril();
            }
            else if (world.isTreeStump() && choice < 603) {
                loadTreeStump();
            }
            else{

            }
        } else if (enemy instanceof Zombie) {
            loadGold(enemy.getGoldAfterDeath());
            loadExp(enemy.getExpAfterDeath());
    
            Random random = new Random();
            int choice = random.nextInt(100);
            if (choice < 5) {
                loadSword();
            }
            else if (choice < 8) {
                loadArmour();
            }
            else if (choice < 10) {
                loadCard();
            }
            else if (choice < 15) {
                loadPotion();
            }
            else{

            }
        } else if (enemy instanceof Doggie) {
            loadExp(enemy.getExpAfterDeath());
            loadDoggieCoin();
            Random random = new Random();
            int choice = random.nextInt(800);
            if (choice < 100) {
                loadSword();
            }
            else if (choice < 200) {
                loadArmour();
            }
            else if (choice < 300) {
                loadShield();
            }
            else if (choice < 400) {
                loadHelmet();
            }
            else if (choice < 500) {
                loadPotion();
            }
            else if (choice < 600) {
                loadCard();
            } 
            else if (world.isTheOneRing() && choice < 601) {
                loadTheOneRing();
            }
            else if (world.isAnduril() && choice < 602) {
                loadAnduril();
            }
            else if (world.isTreeStump() && choice < 603) {
                loadTreeStump();
            }
            else{

            }
            

        } else if (enemy instanceof ElanMuske) {
            loadExp(enemy.getExpAfterDeath());
            loadGold(enemy.getGoldAfterDeath());
            ElanMuske e = (ElanMuske) enemy;
            e.decreaseDoggiePrice(world);
            Random random = new Random();
            int choice = random.nextInt(700);
            if (choice < 100) {
                loadSword();
            }
            else if (choice < 200) {
                loadArmour();
            }
            else if (choice < 300) {
                loadShield();
            }
            else if (choice < 400) {
                loadHelmet();
            }
            else if (choice < 500) {
                loadPotion();
            }
            else if (choice < 600) {
                loadCard();
            } 
            else if (world.isTheOneRing() && choice < 601) {
                loadTheOneRing();
            }
            else if (world.isAnduril() && choice < 602) {
                loadAnduril();
            }
            else if (world.isTreeStump() && choice < 603) {
                loadTreeStump();
            }
            else{

            }
        }
    }

    /**
     * load the card for different type
     */
    public void loadCard() {
        int cardChoice = new Random().nextInt(7);
        if (cardChoice == 0) {
            loadVampireCard();
        }
        else if (cardChoice == 1) {
            loadZombieCard();    
        }
        else if (cardChoice == 2) {
            loadVillageCard();    
        }
        else if (cardChoice == 3) {
            loadBarrackCard();    
        }
        else if (cardChoice == 4) {
            loadTowerCard();    
        }
        else if (cardChoice == 5) {
            loadTrapCard();    
        }
        else if (cardChoice == 6) {
            loadCampFireCard();    
        }
    }

    private void loadZombieCard() {
        // TODO = load more types of card
        ZombiePitCard vampireCastleCard = world.loadZombieCard();
        onLoad(vampireCastleCard);
    }

    private void loadVillageCard() {
        // TODO = load more types of card
        VillageCard villageCard = world.loadVillageCard();
        onLoad(villageCard);
    }

    private void loadBarrackCard() {
        // TODO = load more types of card
        BarrackCard barrackCard = world.loadBarrackCard();
        onLoad(barrackCard);
    }

    private void loadTowerCard() {
        // TODO = load more types of card
        TowerCard towerCard = world.loadTowerCard();
        onLoad(towerCard);
    }

    private void loadTrapCard() {
        // TODO = load more types of card
        TrapCard trapCard = world.loadTrapCard();
        onLoad(trapCard);
    }

    private void loadCampFireCard() {
        // TODO = load more types of card
        CampFireCard campFireCard = world.loadCampFireCard();
        onLoad(campFireCard);
    }

    private void loadGold(int value) {
        this.world.addGold(value);
    }

    private void loadExp(int value) {
        this.world.addExp(value);
    }

//TODO: complete world methods
    


    /**
     * load a vampire castle card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param vampireCastleCard
     */
    private void onLoad(VampireCastleCard vampireCastleCard) {
        ImageView view = new ImageView(vampireCastleCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.VAMPIRECARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    private void onLoad(ZombiePitCard vampireCastleCard) {
        ImageView view = new ImageView(zombiePitCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.ZOMBIECARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    private void onLoad(CampFireCard vampireCastleCard) {
        ImageView view = new ImageView(campfireCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CAMPFIRECARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    private void onLoad(BarrackCard vampireCastleCard) {
        ImageView view = new ImageView(barrackCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.BARRACKCARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    private void onLoad(TrapCard vampireCastleCard) {
        ImageView view = new ImageView(trapCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.TRAPCARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    private void onLoad(TowerCard vampireCastleCard) {
        ImageView view = new ImageView(towerCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.TOWERCARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    private void onLoad(VillageCard vampireCastleCard) {
        ImageView view = new ImageView(villageCardImage);

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.VILLAGECARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a sword into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param sword
     */
    private void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.SWORD, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Armour sword) {
        ImageView view = new ImageView(armourImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ARMOUR, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Shield sword) {
        ImageView view = new ImageView(shieldImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Helmet sword) {
        ImageView view = new ImageView(helmetImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.HELMET, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Stake sword) {
        ImageView view = new ImageView(stakeImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.STAKE, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Staff sword) {
        ImageView view = new ImageView(staffImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.STAFF, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, unequippedInventory, equippedItems);
        addEntity(potion, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(DoggieCoin potion) {
        ImageView view = new ImageView(doggieCoinImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, unequippedInventory, equippedItems);
        addEntity(potion, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Anduril potion) {
        ImageView view = new ImageView(andurilImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ANDURIL, unequippedInventory, equippedItems);
        addEntity(potion, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(TreeStump potion) {
        ImageView view = new ImageView(treeStumpImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.TREESTUMP, unequippedInventory, equippedItems);
        addEntity(potion, view);
        unequippedInventory.getChildren().add(view);
    }


    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoad(Slug enemy) {
        ImageView view = new ImageView(slugImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Zombie enemy) {
        ImageView view = new ImageView(zombieImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Vampire enemy) {
        ImageView view = new ImageView(vampireImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Doggie enemy) {
        ImageView view = new ImageView(doggieImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoad(ElanMuske enemy) {
        ImageView view = new ImageView(elanImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoadPick(Potion enemy) {
        ImageView view = new ImageView(potionImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Gold enemy) {
        ImageView view = new ImageView(goldImage);
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Soldier soldier) {
        ImageView view = new ImageView(soldierImage);
        addEntity(soldier, view);
        soldiers.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building
     */

    private void onLoad(Building newBuilding) {
        if (newBuilding instanceof VampireCastle) {
            VampireCastle a = (VampireCastle) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof ZombiePit) {
            ZombiePit a = (ZombiePit) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof Trap) {
            Trap a = (Trap) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof CampFire) {
            CampFire a = (CampFire) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof Barrack) {
            Barrack a = (Barrack) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof Village){
            Village a = (Village) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof HeroCastle) {
            HeroCastle a = (HeroCastle) newBuilding;
            onLoad(a);
        } else if (newBuilding instanceof Tower) {
            Tower a = (Tower) newBuilding;
            onLoad(a);
        }
    }

    /**
     * on load different type of item
     * @param i
     */
    private void onLoad(Item i) {
        if (i instanceof Sword) {
            Sword a = (Sword) i;
            onLoad(a);
        }
        else if (i instanceof Stake) {
            Stake a = (Stake) i;
            onLoad(a);
        }
        else if (i instanceof Staff) {
            Staff a = (Staff) i;
            onLoad(a);
        }
        else if (i instanceof Helmet) {
            Helmet a = (Helmet) i;
            onLoad(a);
        }
        else if (i instanceof Armour) {
            Armour a = (Armour) i;
            onLoad(a);
        }
        else if (i instanceof Shield) {
            Shield a = (Shield) i;
            onLoad(a);
        }
        else if (i instanceof Potion) {
            Potion a = (Potion) i;
            onLoad(a);
        } else if (i instanceof Anduril) {
            Anduril a = (Anduril) i;
            onLoad(a);
        } else if (i instanceof TreeStump) {
            TreeStump a = (TreeStump) i;
            onLoad(a);
        } else if (i instanceof DoggieCoin) {
            DoggieCoin a = (DoggieCoin) i;
            onLoad(a);
        }
    }

    private void onLoad(VampireCastle building){
        ImageView view = new ImageView(vampireCastleBuildingImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(HeroCastle building){
        ImageView view = new ImageView(herosCastleImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Barrack building){
        ImageView view = new ImageView(barrackImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(ZombiePit building){
        ImageView view = new ImageView(zombiePitImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Tower building){
        ImageView view = new ImageView(towerImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Trap building){
        ImageView view = new ImageView(trapImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(CampFire building){
        ImageView view = new ImageView(campfireImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(Village building){
        ImageView view = new ImageView(villageImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onEquip(Sword s) {
        ImageView view = new ImageView(swordImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(Stake s) {
        ImageView view = new ImageView(stakeImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(Staff s) {
        ImageView view = new ImageView(staffImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(Helmet s) {
        ImageView view = new ImageView(helmetImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(Armour s) {
        ImageView view = new ImageView(armourImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(Shield s) {
        ImageView view = new ImageView(shieldImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(TheOneRing s) {
        ImageView view = new ImageView(theOneRingImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(Anduril s) {
        ImageView view = new ImageView(andurilImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }

    private void onEquip(TreeStump s) {
        ImageView view = new ImageView(treeStumpImage);
        addEntity(s, view);
        equippedItems.getChildren().add(view);
    }
    

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped, consider applying additional if-statement logic
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType){
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != targetGridPane && db.hasImage()){

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        //ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType){
                            case VAMPIRECARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newVC = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newVC);
                                break;
                            case ZOMBIECARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newZP = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newZP);
                                break;
                            case VILLAGECARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newV = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newV);
                                break;
                            case TOWERCARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newT = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newT);
                                break;
                            case CAMPFIRECARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newCF = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newCF);
                                break;
                            case BARRACKCARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newB = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newB);
                                break;
                            case TRAPCARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newTrap = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                
                                onLoad(newTrap);
                                break;
                            case SWORD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Sword newSw = (Sword) world.covertEquippedToEquipped(nodeX, nodeY);

                                onEquip(newSw);
                                break;
                            case STAKE:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Stake newSt = (Stake) world.covertEquippedToEquipped(nodeX, nodeY);

                                onEquip(newSt);
                                break;
                            case STAFF:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Staff newSf = (Staff) world.covertEquippedToEquipped(nodeX, nodeY);

                                onEquip(newSf);
                                break;
                            case HELMET:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Helmet newHel = (Helmet) world.covertEquippedToEquipped(nodeX, nodeY);

                                onEquip(newHel);
                                break;
                            case ARMOUR:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Armour newAr = (Armour) world.covertEquippedToEquipped(nodeX, nodeY);

                                onEquip(newAr);
                                break;
                            case SHIELD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Shield newSh = (Shield) world.covertEquippedToEquipped(nodeX, nodeY);

                                onEquip(newSh);
                                break;
                            case POTION:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                break;
                            case ANDURIL:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                Anduril newAn = (Anduril) world.covertEquippedToEquipped(nodeX, nodeY);
                                onEquip(newAn);
                                break;
                            case TREESTUMP:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                TreeStump newTS = (TreeStump) world.covertEquippedToEquipped(nodeX, nodeY);
                                onEquip(newTS);
                                break;
                            default:
                                break;
                        }
                        
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    // /**
    //  * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
    //  * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
    //  * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
    //  */
    // private void removeItemByCoordinates(int nodeX, int nodeY) {
    //     world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    // }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case VAMPIRECARD:
                        draggedEntity.setImage(vampireCastleCardImage);
                        break;
                    case ZOMBIECARD:
                        draggedEntity.setImage(zombiePitCardImage);
                        break;
                    case VILLAGECARD:
                        draggedEntity.setImage(villageCardImage);
                        break;
                    case TOWERCARD:
                        draggedEntity.setImage(towerCardImage);
                        break;
                    case BARRACKCARD:
                        draggedEntity.setImage(barrackCardImage);
                        break;
                    case CAMPFIRECARD:
                        draggedEntity.setImage(campfireCardImage);
                        break;
                    case TRAPCARD:
                        draggedEntity.setImage(trapCardImage);
                        break;
                    case SWORD:
                        draggedEntity.setImage(swordImage);
                        break;
                    case STAKE:
                        draggedEntity.setImage(stakeImage);
                        break;
                    case STAFF:
                        draggedEntity.setImage(staffImage);
                        break;
                    case ARMOUR:
                        draggedEntity.setImage(armourImage);
                        break;
                    case HELMET:
                        draggedEntity.setImage(helmetImage);
                        break;
                    case SHIELD:
                        draggedEntity.setImage(shieldImage);
                        break;
                    case POTION:
                        draggedEntity.setImage(potionImage);
                        break;
                    case DOGGIECOIN:
                        draggedEntity.setImage(doggieCoinImage);
                        break;
                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage()){
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you could program the game so if the new highlight location is invalid the highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                startTimer();
            }
            else{
                pause();
            }
            break;
        case H:
            world.consumePotion();  
            break;
        default:
            break;
        }
    }

    /**
     * switchers to switch to another window
     * @param mainMenuSwitcher
     */
    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * switchers to switch to another window
     * @param storeSwitcher
     */
    public void setStoreSwitcher(StoreSwitcher storeSwitcher) {
        this.storeSwitcher = storeSwitcher;
    }

    /**
     * switchers to switch to another window
     * @param mainMenuSwitcher
     */
    public void setWinSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.winSwitcher = mainMenuSwitcher;
    }

    /**
     * switchers to switch to another window
     * @param mainMenuSwitcher
     */
    public void setLoseSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.loseSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    //@FXML
    private void switchToStore() {
        // TODO = possibly set other menu switchers
        pause();
        storeSwitcher.switchStore();
    }

    private void switchToWin() {
        // TODO = possibly set other menu switchers
        pause();
        winSwitcher.switchMenu();
    }

    private void switchToLose() {
        // TODO = possibly set other menu switchers
        pause();
        loseSwitcher.switchMenu();
    }



    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                    soldiers.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                   soldiers.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    public LoopManiaWorld getWorld() {
        return world;
    }

    public void restart() {
        world.restart();
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }
}
