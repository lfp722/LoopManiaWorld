package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WinController {
    private MenuSwitcher mainMenuSwitcher;
    private Sound sound;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private void switchToMainMenu(){
        sound.stopWin();
        mainMenuSwitcher.switchMenu();
    }

    public void setWinMenu(MenuSwitcher menu) {
        mainMenuSwitcher = menu;
    }
}

