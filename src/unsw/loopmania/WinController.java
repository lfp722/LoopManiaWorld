package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WinController {
    private MenuSwitcher mainMenuSwitcher;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private void switchToMainMenu(){
        mainMenuSwitcher.switchMenu();
    }
}
