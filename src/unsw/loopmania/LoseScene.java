package unsw.loopmania;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.stage.Stage;

public class LoseScene {
    private Stage stage;
    private String title;
    private Scene scene;
    private MainMenuController controller;

    public LoseScene() throws IOException{
        this.title = "Player is dead!";
        this.controller = new MainMenuController();

        //https://stackoverflow.com/questions/33683302/loading-fxml-file-in-main
        FXMLLoader l = new FXMLLoader(getClass().getResource("LoseScene.fxml"));
        l.setController(controller);

        Parent r = l.load();
        scene = new Scene(r);
    }

    public void start(){
        stage.setTitle(title);
        stage.setScene(scene);
    }
    
}
