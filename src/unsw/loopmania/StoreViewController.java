package unsw.loopmania;
import unsw.loopmania.items.*;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;


import javafx.scene.text.Text;

import javafx.beans.property.SimpleIntegerProperty;

public class StoreViewController  {

    private StoreSwitcher gameSwitcher;


    @FXML
    private Text goldTotal;


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

        goldTotal = new Text("0");
        goldTotal.textProperty().bind(gold.asString());
        

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
            world.getEquip().getArmour().levelUp();
            gold.set(gold.get()-world.getEquip().getArmour().nextLevelUpPrice());
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
            world.getEquip().getHelmet().levelUp();
            gold.set(gold.get()-world.getEquip().getHelmet().nextLevelUpPrice());
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
            world.getEquip().getShield().levelUp();
            gold.set(gold.get()-world.getEquip().getShield().nextLevelUpPrice());
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
        else if (gold.get() >= world.getEquip().getWeapon().nextLevelUpPrice()) {
            world.getEquip().getWeapon().levelUp();
            gold.set(gold.get()-world.getEquip().getWeapon().nextLevelUpPrice());
        }
        else {
            //popUpWindow();
        }
    }


    @FXML
    void initialize() {
    }

}