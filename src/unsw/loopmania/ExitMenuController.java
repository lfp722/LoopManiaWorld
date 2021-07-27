package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class ExitMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher resumeGameSwitcher;

    private MenuSwitcher restartGameSwitcher;

    private MenuSwitcher saveGameSwitcher;
    

    public void setResumeGameSwitcher(MenuSwitcher gameSwitcher){
        this.resumeGameSwitcher = gameSwitcher;
    }

    public void setRestartGameSwitcher(MenuSwitcher gameSwitcher){
        this.restartGameSwitcher = gameSwitcher;
    }

    public void setSaveGameSwitcher(MenuSwitcher gameSwitcher) {
        this.saveGameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void resume() throws IOException {
        resumeGameSwitcher.switchMenu();
    }

    @FXML
    private void quit() throws IOException {
        System.exit(0);
    }

    @FXML
    private void restart() throws IOException {
        restartGameSwitcher.switchMenu();
    }

    @FXML
    private void save() throws IOException {
        saveGameSwitcher.switchMenu();
    }

    
}
