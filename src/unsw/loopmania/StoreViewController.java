package unsw.loopmania;
import unsw.loopmania.items.*;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import org.junit.jupiter.params.aggregator.ArgumentAccessException;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.beans.property.SimpleIntegerProperty;

public class StoreViewController  {

    private StoreSwitcher gameSwitcher;
    //private ArrayList<Item> cart;

    public static final int SWORD = 0;
    public static final int STAKE = 1;
    public static final int STAFF = 2;
    public static final int HELMET = 3;
    public static final int ARMOUR = 4;
    public static final int SHIELD = 5;
    public static final int POTION = 6;
    
    @FXML
    private GridPane InventoryGrid;

    @FXML
    private Text Num_Gold;

    private SimpleIntegerProperty characterGold;


    @FXML
    private Text Num_Purchase;
    
    private SimpleIntegerProperty curPrice;


    @FXML
    private Text Num_Sell;
    
    private SimpleIntegerProperty sellPrice;

    @FXML
    private Text Num_Upgrade;

    private SimpleIntegerProperty upgradePrice;

    @FXML
    private Button Button_Sell;
    @FXML
    private Button Button_Buy;
    @FXML
    private Button Button_Upgrade;

    @FXML
    private CheckBox ItemStore_Portion;
    private Potion potion;

    @FXML
    private CheckBox ItemStore_Sword;
    private Sword sword;

    @FXML
    private CheckBox ItemStore_Staff;
    private Staff staff;

    @FXML
    private CheckBox ItemStore_Stake;
    private Stake stake;

    @FXML
    private CheckBox ItemStore_Shield;
    private Shield shield;

    @FXML
    private CheckBox ItemStore_Armour;
    private Armour armour;

    @FXML
    private CheckBox ItemStore_Helmet;
    private Helmet helmet;

    public void setGameSwitcher(StoreSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    private void switchToGame() throws IOException {
        this.gameSwitcher.switchStore();
    }

    private LoopManiaWorld world;

    private List<Integer> cart;

    private List<Item> bought;

    public StoreViewController(LoopManiaWorld world) {
        this.cart = new ArrayList<>();
        bought = new ArrayList<>();
        

        curPrice = new SimpleIntegerProperty(0);
        upgradePrice = new SimpleIntegerProperty(0);
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(ARMOUR, this.ItemStore_Armour.selectedProperty().get()), this.ItemStore_Armour.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(HELMET, this.ItemStore_Helmet.selectedProperty().get()), this.ItemStore_Helmet.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(POTION, this.ItemStore_Portion.selectedProperty().get()), this.ItemStore_Portion.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(SHIELD,this.ItemStore_Shield.selectedProperty().get()), this.ItemStore_Shield.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(STAFF,this.ItemStore_Staff.selectedProperty().get()), this.ItemStore_Staff.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(STAKE,this.ItemStore_Stake.selectedProperty().get()), this.ItemStore_Stake.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(SWORD,this.ItemStore_Sword.selectedProperty().get()), this.ItemStore_Sword.selectedProperty()));

        this.Num_Purchase.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.curPrice.get()), this.curPrice));
        
        this.Num_Upgrade.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.upgradePrice.get()), this.upgradePrice));
        //this.Num_Sell.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.sellPrice.get()), this.sellPrice));
        
        this.Num_Gold.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(world.getCharacter().getGold()), world.getCharacter().getG()));

        this.world = world;
    }
    
    
    


    @FXML
    public void Buy(ActionEvent event) {
        if (world.getCharacter().getGold() >= (this.curPrice.get())) {
            // world.getCharacter().getG().set(world.getCharacter().getGold() - this.curPrice.get());
            // for (Item it: this.cart) {
            //     world.getCharacter().addItem(it);
            // }
            // this.setCart(new ArrayList<>());
            // this.resetCurPrice();
            // this.setInventoryGrid();

            for (int i: cart) {
                switch(i) {
                case SWORD:
                    Sword sw = world.addUnequippedSword();
                    bought.add(sw);
                    ItemStore_Sword.selectedProperty().set(false);
                    break;
                case STAKE:
                    Stake sta = world.addUnequippedStake();
                    bought.add(sta);
                    ItemStore_Stake.selectedProperty().set(false);
                    break;
                case STAFF:
                    Staff stf = world.addUnequippedStaff();
                    bought.add(stf);
                    ItemStore_Staff.selectedProperty().set(false);
                    break;
                case HELMET:
                    Helmet h = world.addUnequippedHelmet();
                    bought.add(h);
                    ItemStore_Helmet.selectedProperty().set(false);
                    break;
                case ARMOUR:
                    Armour a = world.addUnequippedArmour();
                    bought.add(a);
                    ItemStore_Armour.selectedProperty().set(false);
                    break;
                case SHIELD:
                    Shield sh = world.addUnequippedShield();
                    bought.add(sh);
                    ItemStore_Shield.selectedProperty().set(false);
                    break;
                case POTION:
                    Potion p = world.addPotion();
                    bought.add(p);
                    ItemStore_Portion.selectedProperty().set(false);
                    break;
                default:
                    break;
                }
            }
            cart.clear();

        }
        else {
            throw new RuntimeException("No enough money for Purchase!");
        }
    }

    public void resetCurPrice() {
        this.curPrice.set(0);
    }


    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
    
        for (Node node : childrens) {
            
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
    
        return result;
    }

    @FXML
    public void Upgrade(ActionEvent event) {
        if (world.getCharacter().getGold() < this.upgradePrice.get()) {
            throw new RuntimeException("No enough money for Upgrade!");
        }
        // for (int i = 0; i < 6; i++) {
		// 	for (int j = 0; j < 3; j++) {
        //         Node n = getNodeByRowColumnIndex(i, j, this.InventoryGrid);
        //         if (n instanceof CheckBox) {
        //             CheckBox slot = (CheckBox) n;
        //             if (slot.selectedProperty().get() == true) {
        //                 Item itemProperty = world.getCharacter().getInventory(i,j);
        //                 Item item = itemProperty.getItem();
        //                 if (item instanceof Equipment) {
        //                     Equipment equipment = (Equipment) item;
        //                     equipment.levelUp();
        //                 }
        //             }
        //         }
        //     }
        // }

        if (ItemStore_Armour.selectedProperty().get()) {
            Equipment e = world.getEquip().getArmour();
            if (e != null) {
                e.levelUp();
            }
        }
        else if (ItemStore_Helmet.selectedProperty().get()) {
            Equipment e = world.getEquip().getHelmet();
            if (e != null) {
                e.levelUp();
            }
        }
        else if (ItemStore_Shield.selectedProperty().get()) {
            Equipment e = world.getEquip().getShield();
            if (e != null) {
                e.levelUp();
            }
        }
        else if (ItemStore_Sword.selectedProperty().get()) {
            Equipment e = world.getEquip().getWeapon();
            if (e != null && e instanceof Sword) {
                e.levelUp();
            }
        }
        else if (ItemStore_Stake.selectedProperty().get()) {
            Equipment e = world.getEquip().getWeapon();
            if (e != null && e instanceof Stake) {
                e.levelUp();
            }
        }
        else if (ItemStore_Staff.selectedProperty().get()) {
            Equipment e = world.getEquip().getWeapon();
            if (e != null && e instanceof Staff) {
                e.levelUp();
            }
        }

        world.getCharacter().getG().set(world.getCharacter().getGold() - this.upgradePrice.get());
        this.setInventoryGrid();
    }


    public void setInventoryGrid(){
        
        this.upgradePrice.unbind();
        this.sellPrice.unbind();
        // for (int i = 0; i < 6; i++) {
		// 	for (int j = 0; j < 3; j++) {
        //         if (itemProperty != null) {
        //             CheckBox checkbox = new CheckBox();
        //             ImageView backgroundImage = new ImageView(itemProperty.getImage());
        //             checkbox.setGraphic(backgroundImage);

        //             this.InventoryGrid.getChildren().remove(i, j);
        //             this.InventoryGrid.add(checkbox, i, j);  
                    
        //             Item item = itemProperty.getItem();
        //             this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(item,checkbox.selectedProperty().get()), checkbox.selectedProperty()));
        //         }
		// 	}
		// }
        ItemStore_Armour = new CheckBox();
        Image armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        ImageView arImage = new ImageView(armourImage);
        ItemStore_Armour.setGraphic(arImage);

        ItemStore_Helmet = new CheckBox();
        Image helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        ImageView helImage = new ImageView(helmetImage);
        ItemStore_Helmet.setGraphic(helImage);

        ItemStore_Shield = new CheckBox();
        Image shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        ImageView shImage = new ImageView(shieldImage);
        ItemStore_Shield.setGraphic(shImage);

        ItemStore_Sword = new CheckBox();
        Image swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        ImageView swImage = new ImageView(swordImage);
        ItemStore_Sword.setGraphic(swImage);

        ItemStore_Stake = new CheckBox();
        Image stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        ImageView stImage = new ImageView(stakeImage);
        ItemStore_Stake.setGraphic(stImage);

        ItemStore_Staff = new CheckBox();
        Image staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        ImageView sfImage = new ImageView(staffImage);
        ItemStore_Staff.setGraphic(sfImage);

        ItemStore_Portion = new CheckBox();
        Image potionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        ImageView pImage = new ImageView(potionImage);
        ItemStore_Portion.setGraphic(pImage);

        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(ARMOUR, this.ItemStore_Armour.selectedProperty().get()), this.ItemStore_Armour.selectedProperty()));
        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(HELMET, this.ItemStore_Helmet.selectedProperty().get()), this.ItemStore_Helmet.selectedProperty()));
        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(POTION, this.ItemStore_Portion.selectedProperty().get()), this.ItemStore_Portion.selectedProperty()));
        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(SHIELD,this.ItemStore_Shield.selectedProperty().get()), this.ItemStore_Shield.selectedProperty()));
        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(STAFF,this.ItemStore_Staff.selectedProperty().get()), this.ItemStore_Staff.selectedProperty()));
        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(STAKE,this.ItemStore_Stake.selectedProperty().get()), this.ItemStore_Stake.selectedProperty()));
        this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(SWORD,this.ItemStore_Sword.selectedProperty().get()), this.ItemStore_Sword.selectedProperty()));

    }

    @FXML
    void initialize() {
        this.InventoryGrid.setAlignment(Pos.CENTER);
        this.setInventoryGrid();
    }

    public int setCurPrice(int it, boolean t) {
        if (t) {
            switch (it) {
                case SWORD:
                    return curPrice.get()+Sword.initialPrice;
                case STAKE:
                    return curPrice.get()+Stake.initialPrice;
                case STAFF:
                    return curPrice.get()+Staff.initialPrice;
                case HELMET:
                    return curPrice.get()+Helmet.initialPrice;
                case ARMOUR:
                    return curPrice.get()+Armour.initialPrice;
                case SHIELD:
                    return curPrice.get()+Shield.initialPrice;
                case POTION:
                    return curPrice.get()+Potion.initialPrice;
                default:
                    break;
            }
            cart.add(it);
        }
        cart.remove(it);
        return this.curPrice.get();
    }
    
    public int setUpgradePrice(int it, boolean check) {
        if (check) {
            switch (it) {
                case SWORD:
                    if (world.getEquip().getWeapon() instanceof Sword){
                        return curPrice.get()+world.getEquip().getWeapon().getLevelUpPrice();
                    }                   
                    break;
                case STAKE:
                    if (world.getEquip().getWeapon() instanceof Stake){
                        return curPrice.get()+world.getEquip().getWeapon().getLevelUpPrice();
                    }
                    break;
                case STAFF:
                    if (world.getEquip().getWeapon() instanceof Staff){
                        return curPrice.get()+world.getEquip().getWeapon().getLevelUpPrice();
                    }
                    break;
                case HELMET:
                    if (world.getEquip().getHelmet() != null){
                        return curPrice.get()+world.getEquip().getHelmet().getLevelUpPrice();
                    }
                    break;
                case ARMOUR:
                    if (world.getEquip().getArmour() != null){
                        return curPrice.get()+world.getEquip().getArmour().getLevelUpPrice();
                    }
                    break;
                case SHIELD:
                    if (world.getEquip().getShield() != null){
                        return curPrice.get()+world.getEquip().getShield().getLevelUpPrice();
                    }
                    break;
                default:
                    break;
            }
        }
        return this.upgradePrice.get();
    }
    
}

