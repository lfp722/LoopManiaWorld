package unsw.loopmania;
import unsw.loopmania.items.*;

import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StoreViewController  {

    @FXML
    private GridPane soldBag;

    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image stakeImage;
    private Image staffImage;
    private Image potionImage;
    private Image swordImage;
    private Image doggieCoinImage;
    private Image andurilImage;
    private Image treeStumpImage;

    public static final int NOLIMIT = 9999999;
    private StoreSwitcher gameSwitcher;
    private int limitOfOutfit = NOLIMIT;
    private int limitOfPotion = NOLIMIT;
    private int outfitCount = 0;
    private int potionCount = 0;

    public void setLimitOfOutfit(int limitOfOutfit) {
        this.limitOfOutfit = limitOfOutfit;
    }

    public void setLimitOfPotion(int limitOfPotion) {
        this.limitOfPotion = limitOfPotion;
    }

    public void initializeCounts() {
        this.outfitCount = 0;
        this.potionCount = 0;
    }

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

        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        potionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());
    }
    

    @FXML
    public void buyHelmet() {
        if (outfitCount >= limitOfOutfit) {
            popUpWarning("You cannot buy more than one outfit in BERSERKER MODE!");
            return;
        }
        if (gold.get() >= Helmet.initialPrice) {
            if (popUpSuccess("buy", "helmet")) {
                Helmet h = world.addUnequippedHelmet();
                gold.set(gold.get()-Helmet.initialPrice);
                bought.add(h);
                outfitCount += 1;
                addSellButton(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyArmour() {
        if (outfitCount >= limitOfOutfit) {
            popUpWarning("You cannot buy more than one outfit in BERSERKER MODE!");
            return;
        }
        if (gold.get() >= Armour.initialPrice) {
            if (popUpSuccess("buy", "armour")) {
                Armour h = world.addUnequippedArmour();
                gold.set(gold.get()-Armour.initialPrice);
                bought.add(h); 
                outfitCount += 1;
                addSellButton(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyShield() {
        if (outfitCount >= limitOfOutfit) {
            popUpWarning("You cannot buy more than one outfit in BERSERKER MODE!");
            return;
        }
        if (gold.get() >= Shield.initialPrice) {
            if (popUpSuccess("buy", "shield")) {
                Shield h = world.addUnequippedShield();
                gold.set(gold.get()- Shield.initialPrice);
                bought.add(h); 
                outfitCount += 1;
                addSellButton(h);
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
                addSellButton(h);
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
                addSellButton(h);
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
                addSellButton(h);
            }
            
        }
        else {
            popUpWarning("You don't have enough gold!");
        }
    }

    @FXML
    public void buyPotion() {
        if (potionCount >= limitOfPotion) {
            popUpWarning("You cannot buy more than one potion in SURVIVAL MODE!");
            return;
        }
        if (gold.get() >= Potion.initialPrice) {
            if (popUpSuccess("buy", "potion")) {
                Potion h = world.addPotion();
                gold.set(gold.get()-Potion.initialPrice);
                bought.add(h);
                potionCount += 1;
                addSellButton(h);
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

    private void addImage(Item i, Button b) {
        if (i instanceof Sword) {
            ImageView image = new ImageView(swordImage);
            b.setGraphic(image);
        }
        else if (i instanceof Stake) {
            ImageView image = new ImageView(stakeImage);
            b.setGraphic(image);
        }
        else if (i instanceof Staff) {
            ImageView image = new ImageView(staffImage);
            b.setGraphic(image);
        }
        else if (i instanceof Helmet) {
            ImageView image = new ImageView(helmetImage);
            b.setGraphic(image);
        }
        else if (i instanceof Armour) {
            ImageView image = new ImageView(armourImage);
            b.setGraphic(image);
        }
        else if (i instanceof Anduril) {
            ImageView image = new ImageView(andurilImage);
            b.setGraphic(image);
        }
        else if (i instanceof DoggieCoin) {
            ImageView image = new ImageView(doggieCoinImage);
            b.setGraphic(image);
        }
        else if (i instanceof TreeStump) {
            ImageView image = new ImageView(treeStumpImage);
            b.setGraphic(image);
        }
        else if (i instanceof Shield) {
            ImageView image = new ImageView(shieldImage);
            b.setGraphic(image);
        }
        else if (i instanceof Potion) {
            ImageView image = new ImageView(potionImage);
            b.setGraphic(image);
        }
    }

    public void addSellButton(Item i) {
        Button b = new Button("SELL");
            GridPane.setColumnIndex(b, i.getX());
            GridPane.setRowIndex(b, i.getY());
            b.setText("Sell");
            addImage(i, b);  
            b.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        boolean ans = popUpSuccess("sell", "item");
                        if (ans) {
                            world.removeUnequippedInventoryItemByCoordinates(i.getX(), i.getY());
                            gold.set(gold.get()+(int)(0.7*i.getValueInGold()));
                            bought.remove(i);
                            // b.setText("Sold");
                            // ImageView none = new ImageView();
                            // b.setGraphic(none);
                            // b.setOnAction(null);
                            soldBag.getChildren().remove(b);
                            
                        }

                    }
                }
            );
            soldBag.getChildren().add(b);
    }


    @FXML
    public void initialize() {
        Text goldTotal = new Text("0");
        goldTotal.textProperty().bind(gold.asString());
        goldTotal.setFill(Color.GREEN);
        goldTotal.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        goldTotal.setLayoutX(121);
        goldTotal.setLayoutY(700);
        ap.getChildren().add(goldTotal);

        Text dcTotal = new Text("0");
        dcTotal.textProperty().bind(world.getDoggiePrice().asString());
        dcTotal.setFill(Color.GREEN);
        dcTotal.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        dcTotal.setLayoutX(250);
        dcTotal.setLayoutY(700);
        ap.getChildren().add(dcTotal);

        List<Item> copied = new ArrayList<>(world.getInventory());

        soldBag.getChildren().clear();
        for (Item i: copied) {
            // Button b = new Button("SELL");
            // GridPane.setColumnIndex(b, i.getX());
            // GridPane.setRowIndex(b, i.getY());
            // b.setText("Sell");
            // addImage(i, b);  
            // b.setOnAction(
            //     new EventHandler<ActionEvent>() {
            //         @Override public void handle(ActionEvent e) {
            //             boolean ans = popUpSuccess("sell", "item");
            //             if (ans) {
            //                 world.removeUnequippedInventoryItemByCoordinates(i.getX(), i.getY());
            //                 gold.set(gold.get()+(int)(0.7*i.getValueInGold()));
            //                 b.setText("Sold");
            //                 ImageView none = new ImageView();
            //                 b.setGraphic(none);
            //                 b.setOnAction(null);
                            
            //             }

            //         }
            //     }
            // );
            // soldBag.getChildren().add(b); 
            addSellButton(i);        
        }
    }

}