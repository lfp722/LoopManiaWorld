package unsw.loopmania;

import javafx.fxml.FXML;

public class StartMenuController {
    private MenuSwitcher newSwitcher;
    private MenuSwitcher loadSwitcher;
    private MenuSwitcher aboutSwitcher;

    public void setNewSwitcher(MenuSwitcher switcher) {
        newSwitcher = switcher;
    }

    public void setLoadSwitcher(MenuSwitcher switcher) {
        loadSwitcher = switcher;
    }

    public void setAboutSwitcher(MenuSwitcher switcher) {
        aboutSwitcher = switcher;
    }

    @FXML
    private void switchToMainMenu() {
        newSwitcher.switchMenu();
    }

    @FXML
    private void switchToLoad() {
        loadSwitcher.switchMenu();
    }

    @FXML
    private void switchToAbout() {
        aboutSwitcher.switchMenu();
    }
    
}
