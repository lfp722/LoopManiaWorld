package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoseController {
    private MenuSwitcher mainMenuSwitcher;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private void switchToMainMenu(){
        mainMenuSwitcher.switchMenu();
    }

    public void setWinMenu(MenuSwitcher menu) {
        mainMenuSwitcher = menu;
    }
}

