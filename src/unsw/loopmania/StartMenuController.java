package unsw.loopmania;

import javafx.fxml.FXML;

public class StartMenuController {
    private MenuSwitcher newSwitcher;
    private MenuSwitcher loadSwitcher;
    private MenuSwitcher aboutSwitcher;
    private MenuSwitcher builderSwitcher;

    public void setNewSwitcher(MenuSwitcher switcher) {
        newSwitcher = switcher;
    }

    public void setLoadSwitcher(MenuSwitcher switcher) {
        loadSwitcher = switcher;
    }

    public void setAboutSwitcher(MenuSwitcher switcher) {
        aboutSwitcher = switcher;
    }

    public void setBuilderSwitcher(MenuSwitcher switcher) {
        builderSwitcher = switcher;
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

    @FXML
    private void switchToBuilder() {
        builderSwitcher.switchMenu();
    }
    
}
