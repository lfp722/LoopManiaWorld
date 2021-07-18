package unsw.loopmania;
import unsw.loopmania.items.*;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;

public class StoreViewController  {

    private StoreSwitcher gameSwitcher;


    @FXML
    private AnchorPane ap;


    public void setGameSwitcher(StoreSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    private void switchToGame() throws IOException {
        this.gameSwitcher.switchStore();
    }

    private LoopManiaWorld world;

    private SimpleIntegerProperty gold;

    private List<Item> bought;

    public StoreViewController(LoopManiaWorld world) {
        this.world = world;
        this.gold = world.getCharacter().getG();
        this.bought = world.getBoughtItem();

        // Text goldTotal = new Text("0");
        // goldTotal.textProperty().bind(gold.asString());
        // goldTotal.setLayoutX(121);
        // goldTotal.setLayoutY(529);
        // ap.getChildren().add(goldTotal);
        

    }
    
    



    @FXML
    public void buyHelmet() {
        if (gold.get() >= Helmet.initialPrice) {
            if (popUpSuccess("buy", "helmet")) {
                Helmet h = world.addUnequippedHelmet();
                gold.set(gold.get()-Helmet.initialPrice);
                bought.add(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyArmour() {
        if (gold.get() >= Armour.initialPrice) {
            if (popUpSuccess("buy", "armour")) {
                Armour h = world.addUnequippedArmour();
                gold.set(gold.get()-Armour.initialPrice);
                bought.add(h); 
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyShield() {
        if (gold.get() >= Shield.initialPrice) {
            if (popUpSuccess("buy", "shield")) {
                Shield h = world.addUnequippedShield();
                gold.set(gold.get()- Shield.initialPrice);
                bought.add(h); 
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buySword() {
        if (gold.get() >= Sword.initialPrice) {
            if (popUpSuccess("buy", "sword")) {
                Sword h = world.addUnequippedSword();
                gold.set(gold.get()-Sword.initialPrice);
                bought.add(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyStake() {
        if (gold.get() >= Stake.initialPrice) {
            if (popUpSuccess("buy", "stake")) {
                Stake h = world.addUnequippedStake();
                gold.set(gold.get()-Stake.initialPrice);
                bought.add(h);
            }
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }


    @FXML
    public void buyStaff() {
        if (gold.get() >= Staff.initialPrice) {
            if (popUpSuccess("buy", "staff")) {
                Staff h = world.addUnequippedStaff();
                gold.set(gold.get()-Staff.initialPrice);
                bought.add(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyPotion() {
        if (gold.get() >= Potion.initialPrice) {
            if (popUpSuccess("buy", "potion")) {
                Potion h = world.addPotion();
                gold.set(gold.get()-Potion.initialPrice);
                bought.add(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void upArmour() {
        if (world.getEquip().getArmour() == null) {
            popUpWarning("You have to equip armour first!");
        }
        else if (gold.get() >= world.getEquip().getArmour().nextLevelUpPrice()) {
            if (popUpSuccess("upgrade", "armour")) {
                gold.set(gold.get()-world.getEquip().getArmour().nextLevelUpPrice());
                world.getEquip().getArmour().levelUp();
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void upHelmet() {
        if (world.getEquip().getHelmet() == null) {
            popUpWarning("You have to equip helmet first!");
            
        }
        else if (gold.get() >= world.getEquip().getHelmet().nextLevelUpPrice()) {
            if (popUpSuccess("upgrade", "helmet")) {
                gold.set(gold.get()-world.getEquip().getHelmet().nextLevelUpPrice());
                world.getEquip().getHelmet().levelUp();
            }
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void upShield() {
        if (world.getEquip().getShield() == null) {
            popUpWarning("You have to equip shield first!");
        }
        else if (gold.get() >= world.getEquip().getShield().nextLevelUpPrice()) {
            if (popUpSuccess("upgrade", "shield")) {
                gold.set(gold.get()-world.getEquip().getShield().nextLevelUpPrice());
                world.getEquip().getShield().levelUp();
            }
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void upWeapon() {
        if (world.getEquip().getWeapon() == null) {
            popUpWarning("You have to equip weapon first!");
        }
        else if (!(gold.get() < world.getEquip().getWeapon().nextLevelUpPrice())) {
            if (popUpSuccess("upgrade", "weapon")) {
                gold.set(gold.get()-world.getEquip().getWeapon().nextLevelUpPrice());
                world.getEquip().getWeapon().levelUp();
            }
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    public void popUpWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean popUpSuccess(String action, String object) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to " + action + " this " + object + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        } else {
            return true;
        }
    }

    @FXML
    void initialize() {
        Text goldTotal = new Text("0");
        goldTotal.textProperty().bind(gold.asString());
        goldTotal.setFill(Color.GREEN);
        goldTotal.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        goldTotal.setLayoutX(121);
        goldTotal.setLayoutY(529);
        ap.getChildren().add(goldTotal);
    }

}