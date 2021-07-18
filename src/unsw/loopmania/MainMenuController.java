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

    private String modeType;
    private MenuSwitcher gameSwitcher;



    public MainMenuController(LoopManiaWorld game){
        this.gameIntance = game;
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */

    private void BerserkerMode() {
        this.modeType = "BerserkerMode";
        this.switchToGame();
    }

    private void NormalMode() {
        this.modeType = "NormalMode";
        this.switchToGame();
    }

    private void SurvivialMode() {
        this.modeType = "SurvivialMode";
        this.switchToGame();
    }

    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    public String getModeType() {
        return this.modeType;
    }
}
