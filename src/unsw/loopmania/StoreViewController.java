package unsw.loopmania;
import unsw.loopmania.items.*;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Button;
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
            Helmet h = world.addUnequippedHelmet();
            gold.set(gold.get()-Helmet.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void buyArmour() {
        if (gold.get() >= Armour.initialPrice) {
            Armour h = world.addUnequippedArmour();
            gold.set(gold.get()-Armour.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void buyShield() {
        if (gold.get() >= Shield.initialPrice) {
            Shield h = world.addUnequippedShield();
            gold.set(gold.get()- Shield.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void buySword() {
        if (gold.get() >= Sword.initialPrice) {
            Sword h = world.addUnequippedSword();
            gold.set(gold.get()-Sword.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void buyStake() {
        if (gold.get() >= Stake.initialPrice) {
            Stake h = world.addUnequippedStake();
            gold.set(gold.get()-Stake.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }


    @FXML
    public void buyStaff() {
        if (gold.get() >= Staff.initialPrice) {
            Staff h = world.addUnequippedStaff();
            gold.set(gold.get()-Staff.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void buyPotion() {
        if (gold.get() >= Potion.initialPrice) {
            Potion h = world.addPotion();
            gold.set(gold.get()-Potion.initialPrice);
            bought.add(h);
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void upArmour() {
        if (world.getEquip().getArmour() == null) {
            //popUpWindow();
        }
        else if (gold.get() >= world.getEquip().getArmour().nextLevelUpPrice()) {
            
            gold.set(gold.get()-world.getEquip().getArmour().nextLevelUpPrice());
            world.getEquip().getArmour().levelUp();
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void upHelmet() {
        if (world.getEquip().getHelmet() == null) {
            //popUpWindow();
        }
        else if (gold.get() >= world.getEquip().getHelmet().nextLevelUpPrice()) {
            
            gold.set(gold.get()-world.getEquip().getHelmet().nextLevelUpPrice());
            world.getEquip().getHelmet().levelUp();
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void upShield() {
        if (world.getEquip().getShield() == null) {
            //popUpWindow();
        }
        else if (gold.get() >= world.getEquip().getShield().nextLevelUpPrice()) {
            
            gold.set(gold.get()-world.getEquip().getShield().nextLevelUpPrice());
            world.getEquip().getShield().levelUp();
        }
        else {
            //popUpWindow();
        }
    }

    @FXML
    public void upWeapon() {
        if (world.getEquip().getWeapon() == null) {
            //popUpWindow();
        }
        else if (!(gold.get() < world.getEquip().getWeapon().nextLevelUpPrice())) {
            gold.set(gold.get()-world.getEquip().getWeapon().nextLevelUpPrice());
            world.getEquip().getWeapon().levelUp();
        }
        else {
            //popUpWindow();
        }
    }

    public void popUpWindow(String message, final Stage primaryStage) {
        final Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);
        popup.getContent().addAll(new Button("Cancel"), new Text());
        
        
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