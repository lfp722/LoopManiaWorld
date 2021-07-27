package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoseController {
    private MenuSwitcher mainMenuSwitcher;

    private MenuSwitcher retrySwitcher;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private void switchToMainMenu(){
        mainMenuSwitcher.switchMenu();
    }

    @FXML
    private void retry() {
        retrySwitcher.switchMenu();
    }

    public void setWinMenu(MenuSwitcher menu) {
        mainMenuSwitcher = menu;
    }

    public void setRetry(MenuSwitcher menu) {
        retrySwitcher = menu;
    }
}

