package unsw.loopmania;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LoadMenuController {

    @FXML
    GridPane archive;

    private MenuSwitcher startSwitcher;

    private StringConvey loadSwitcher;
    
    public void setStartSwitcher(MenuSwitcher switcher) {
        startSwitcher = switcher;
    } 

    public void setLoadSwitcher(StringConvey switcher) {
        loadSwitcher = switcher;
    }

    @FXML
    private void switchToStart() {
        startSwitcher.switchMenu();
    }


    @FXML
    public void initialize() {
        final File folder = new File("/tmp_amd/glass/export/glass/3/z5271819/comp2511/proj/techT/21T2-cs2511-project/archive");
        int rowIndex = 0;
        int columnIndex = 0;
        for (final File fileEntry: folder.listFiles()) {
            Button arButton = new Button(fileEntry.getName());
            GridPane.setColumnIndex(arButton, columnIndex);
            GridPane.setRowIndex(arButton, rowIndex);
            arButton.setPrefWidth(596);
            arButton.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e) {
                        loadSwitcher.convey(fileEntry.getName());
                    }
                }
            );
            archive.getChildren().add(arButton);
            rowIndex += 1;

        }
    }

}
