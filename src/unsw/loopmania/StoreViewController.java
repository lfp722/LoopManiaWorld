package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.items.Equipments.Equipment;
import unsw.loopmania.items.Equipments.outfits.*;
import unsw.loopmania.items.basics.*;
import unsw.loopmania.items.Equipments.weapons.*;
import unsw.loopmania.items.Item;
public class StoreViewController  {

    private StoreSwitcher storeSwitcher;
    private Character character;
    private ArrayList<Item> cart;
    
    

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
    private Portion portion;

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

    public void setGameSwitcher(StoreSwitcher storeSwitcher){
        this.storeSwitcher = storeSwitcher;
    }

    @FXML
    private void switchToGame() throws IOException {
        this.storeSwitcher.switchStore();
    }

    public StoreViewController(StoreSwitcher storeSwitcher, Character ch) {
        this.setGameSwitcher(storeSwitcher);
        this.character = ch;
        this.cart = new ArrayList<>();

        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.armour, this.ItemStore_Armour.selectedProperty().get()), this.ItemStore_Armour.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.helmet, this.ItemStore_Helmet.selectedProperty().get()), this.ItemStore_Helmet.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.portion, this.ItemStore_Portion.selectedProperty().get()), this.ItemStore_Portion.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.shield,this.ItemStore_Shield.selectedProperty().get()), this.ItemStore_Shield.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.staff,this.ItemStore_Staff.selectedProperty().get()), this.ItemStore_Staff.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.stake,this.ItemStore_Stake.selectedProperty().get()), this.ItemStore_Stake.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.setCurPrice(this.sword,this.ItemStore_Sword.selectedProperty().get()), this.ItemStore_Sword.selectedProperty()));
        this.curPrice.set(0);

        this.Num_Purchase.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.curPrice.get()), this.curPrice));
        
        this.Num_Upgrade.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.upgradePrice.get()), this.upgradePrice));
        this.Num_Sell.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.sellPrice.get()), this.sellPrice));
        
        this.characterGold.bindBidirectional(this.character.getGoldProperty());
        this.Num_Gold.textProperty().bind(Bindings.createStringBinding(()->String.valueOf(this.character.getGold()), this.characterGold));


    }
    
    
    @FXML
    public void Buy(ActionEvent event) {
        if (this.character.getGold() >= (this.curPrice.get())) {
            this.character.setGold(this.character.getGold() - this.curPrice.get());
            for (Item it: this.cart) {
                this.character.addItem(it);
            }
            this.setCart(new ArrayList<>());
            this.resetCurPrice();
        }
        else {
            throw new RuntimeException("No enough money!");
        }
    }

    public void resetCurPrice() {
        this.curPrice.set(0);
    }

    public void setCart(ArrayList<Item> cart) {
        this.cart = cart;
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
    
        for (Node node : childrens) {
            
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
    
        return result;
    }

    @FXML
    public void Sell(ActionEvent event) {
        for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
                Node n = getNodeByRowColumnIndex(i, j, this.InventoryGrid);
                if (n instanceof CheckBox) {
                    CheckBox slot = (CheckBox) n;
                    if (slot.selectedProperty().get() == true) {
                        ItemProperty item = this.character.getInventory(i,j);
                        Item it = item.getItem();
                        this.character.eliminateItem(item);
                    }
                }
            }
        }
        this.character.setGold(this.character.getGold() + this.sellPrice.get());
        this.setInventoryGrid();
    }

    @FXML
    public void Upgrade(ActionEvent event) {
        for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
                Node n = getNodeByRowColumnIndex(i, j, this.InventoryGrid);
                if (n instanceof CheckBox) {
                    CheckBox slot = (CheckBox) n;
                    if (slot.selectedProperty().get() == true) {
                        ItemProperty itemProperty = this.character.getInventory(i,j);
                        Item item = itemProperty.getItem();
                        if (item instanceof Equipment) {
                            Equipment equipment = (Equipment) item;
                            equipment.setLevel();
                        }
                    }
                }
            }
        }
        this.character.setGold(this.character.getGold() - this.upgradePrice.get());
        this.setInventoryGrid();
    }


    public void setInventoryGrid(){
        for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
                ItemProperty itemProperty = this.character.getInventory(i,j);
                if (itemProperty != null) {
                    CheckBox checkbox = new CheckBox();
                    ImageView backgroundImage = new ImageView(itemProperty.getImage());
                    checkbox.setGraphic(backgroundImage);
                    checkbox.selectedProperty().bindBidirectional(itemProperty);
                    this.InventoryGrid.getChildren().remove(i, j);
                    this.InventoryGrid.add(checkbox, i, j);  
                    
                    Item item = itemProperty.getItem();

                    this.upgradePrice.bind(Bindings.createIntegerBinding(()->this.setUpgradePrice(item,checkbox.selectedProperty().get()), checkbox.selectedProperty()));
                    this.sellPrice.bind(Bindings.createIntegerBinding(()->this.setSellPrice(item,checkbox.selectedProperty().get()), checkbox.selectedProperty()));
                    
                    this.upgradePrice.set(0);
                    this.sellPrice.set(0);
                }
			}
		}
        
    }

    public int setUpgradePrice(Item item, boolean check) {
        if (check) {
            return this.upgradePrice.get() + item.getValueInGold();
        }
        return this.upgradePrice.get() - item.getValueInGold();
    }

    public int setSellPrice(Item item, boolean check) {
        if (check) {
            return this.sellPrice.get() + item.getValueInGold();
        }
        return this.sellPrice.get() - item.getValueInGold();
    }
    
    @FXML
    void initialize() {
        this.InventoryGrid.setAlignment(Pos.CENTER);
        this.setInventoryGrid();
    }

    public int setCurPrice(Item it, boolean t) {
        if (t) {
            this.cart.add(it);
            return this.curPrice.get() + it.getValueInGold();
        }
        this.cart.remove(it);
        return this.curPrice.get() - it.getValueInGold();
    }

}
