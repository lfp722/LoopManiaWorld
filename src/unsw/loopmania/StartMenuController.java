package unsw.loopmania;

import javafx.fxml.FXML;

public class StartMenuController {
    private MenuSwitcher newSwitcher;
    private MenuSwitcher loadSwitcher;

    public void setNewSwitcher(MenuSwitcher switcher) {
        newSwitcher = switcher;
    }

    public void setLoadSwitcher(MenuSwitcher switcher) {
        loadSwitcher = switcher;
    }

    @FXML
    private void switchToMainMenu() {
        newSwitcher.switchMenu();
    }

    @FXML
    private void switchToLoad() {
        loadSwitcher.switchMenu();
    }
    
}
