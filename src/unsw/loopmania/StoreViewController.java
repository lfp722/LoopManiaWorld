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
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.items.Equipments.outfits.*;
import unsw.loopmania.items.basics.*;
import unsw.loopmania.items.Equipments.weapons.*;
import unsw.loopmania.items.Item;
public class StoreViewController  {

    private StoreSwitcher storeSwitcher;
    private Character character;
    private ArrayList<Item> cart;
    private ArrayList<Item> sellingCart;
    private SimpleIntegerProperty curPrice;
    @FXML
    private GridPane InventoryGrid;

    @FXML
    private Button Button_Sell;
    @FXML
    private Button Button_Buy;

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
        this.sellingCart = new ArrayList<>();
        this.curPrice.set(0);
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
    @FXML
    public void Sell(ActionEvent event) {
        for (Item it: this.sellingCart) {
            this.character.setGold(this.character.getGold() + it.getValueInGold());
            this.character.eliminateItem(it);
            
        }
        this.setSellingCart(new ArrayList<>());
    }

    public void setSellingCart(ArrayList<Item> sellingCart) {
        this.sellingCart = sellingCart;
    }
    @FXML
    void initialize() {
        this.InventoryGrid.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				CheckBox checkbox = new CheckBox();
                ImageView backgroundImage = new ImageView();
                checkbox.selectedProperty().bindBidirectional(this.character.getInventory.(i,j).getProperty());
                this.InventoryGrid.add(checkbox, i, j);  
			}
		}
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.armour), this.ItemStore_Armour.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.helmet), this.ItemStore_Helmet.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.portion), this.ItemStore_Portion.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.shield), this.ItemStore_Shield.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.staff), this.ItemStore_Staff.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.stake), this.ItemStore_Stake.selectedProperty()));
        this.curPrice.bind(Bindings.createIntegerBinding(()->this.addCurPrice(this.sword), this.ItemStore_Sword.selectedProperty()));
    }

    public int addCurPrice(Item it) {
        return this.curPrice.get() + it.getValueInGold();
    }

}
