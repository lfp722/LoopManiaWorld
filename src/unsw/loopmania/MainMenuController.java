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
    private LoopManiaWorld gameIntance;


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

    private BerserkerMode() {
        this.modeType = "BerserkerMode";
        this.gameIntance.setMode(this.modeType);
    }

    private NormalMode() {
        this.gameIntance.setMode("BersekerMode");
        this.gameIntance.setMode(this.modeType);
    }

    private SurvivialMode() {
        this.gameIntance.setMode("SurvivialMode");
        this.gameIntance.setMode(this.modeType);
    }

    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }
}
