package unsw.loopmania;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.stage.Stage;
import unsw.loopmania.goal.*;

public class WinScene {
    private Stage stage;
    private String title;
    private Scene scene;
    private MainMenuController controller;

    private LoopManiaWorld world;

    public WinScene() throws IOException{
        CycleGoal cycle = new CycleGoal(world.getCycle().intValue());
        ExpGoal exp = new ExpGoal(world.getCharacter().getExp());
        GoldGoal gold = new GoldGoal(world.getCharacter().getGold());
        if(!cycle.checkGoal(world)){
            throw new Error();
        }
        if(!exp.checkGoal(world)){
            throw new Error();
        }
        if(!gold.checkGoal(world)){
            throw new Error();
        }
        this.title = "Player Win";
        this.controller = new MainMenuController();

        //https://stackoverflow.com/questions/33683302/loading-fxml-file-in-main
        FXMLLoader l = new FXMLLoader(getClass().getResource("WinScene.fxml"));
        l.setController(controller);

        Parent r = l.load();
        scene = new Scene(r);
    }

    public void start(){
        stage.setTitle(title);
        stage.setScene(scene);
    }
}
