package unsw.loopmania;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LoadMapController {
    
    @FXML
    GridPane archive;

    private MenuSwitcher startSwitcher;

    private StringConvey mapSwitcher;
    
    public void setStartSwitcher(MenuSwitcher switcher) {
        startSwitcher = switcher;
    } 

    public void setMapSwitcher(StringConvey switcher) {
        mapSwitcher = switcher;
    }

    @FXML
    private void switchToStart() {
        startSwitcher.switchMenu();
    }


    @FXML
    public void initialize() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        String path = s + "/worlds";
        final File folder = new File(path);
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
                        mapSwitcher.convey(path+'/'+fileEntry.getName());
                    }
                }
            );
            archive.getChildren().add(arButton);
            rowIndex += 1;

        }
    }

}
