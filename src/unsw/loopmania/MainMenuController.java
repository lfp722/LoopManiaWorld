package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher normalGameSwitcher;
    private MenuSwitcher berserkerGameSwitcher;
    private MenuSwitcher survivalGameSwitcher;

    public void setNormalGameSwitcher(MenuSwitcher gameSwitcher){
        this.normalGameSwitcher = gameSwitcher;
    }

    public void setBerserkerGameSwitcher(MenuSwitcher gameSwitcher){
        this.berserkerGameSwitcher = gameSwitcher;
    }

    public void setSurvivalGameSwitcher(MenuSwitcher gameSwitcher){
        this.survivalGameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToNormalGame() throws IOException {
        normalGameSwitcher.switchMenu();
    }

    @FXML
    private void switchToBerserkerGame() throws IOException {
        berserkerGameSwitcher.switchMenu();
    }

    @FXML
    private void switchToSurvivalGame() throws IOException {
        survivalGameSwitcher.switchMenu();
    }
}
