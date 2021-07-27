package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoseController {
    private MenuSwitcher mainMenuSwitcher;
    private Sound sound;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private void switchToMainMenu(){
        sound.stopLost();
        mainMenuSwitcher.switchMenu();
    }

    public void setWinMenu(MenuSwitcher menu) {
        sound.stopLost();
        mainMenuSwitcher = menu;
    }
}

