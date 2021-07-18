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
    

    public void setResumeGameSwitcher(MenuSwitcher gameSwitcher){
        this.resumeGameSwitcher = gameSwitcher;
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

    
}
