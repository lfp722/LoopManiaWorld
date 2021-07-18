package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load the store
        StoreViewController storeController = new StoreViewController(mainController.getWorld());
        FXMLLoader storeLoader = new FXMLLoader(getClass().getResource("StoreView.fxml"));
        storeLoader.setController(storeController);
        Parent storeRoot = storeLoader.load();

        // load win window when goal achieved
        WinController winController = new WinController();
        FXMLLoader winLoader = new FXMLLoader(getClass().getResource("WinScene.fxml"));
        winLoader.setController(winController);
        Parent winRoot = winLoader.load();

        // load lose vindow then the character dead
        LoseController loseController = new LoseController();
        FXMLLoader loseLoader = new FXMLLoader(getClass().getResource("LoseScene.fxml"));
        loseLoader.setController(loseController);
        Parent loseRoot = loseLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);

        //Scene scene = new Scene(storeRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        mainController.setStoreSwitcher(() -> {
            mainController.pause();
            switchToRoot(scene, storeRoot, primaryStage);
        });
        mainController.setWinSwitcher(() -> {
            stop();
            switchToRoot(scene, winRoot, primaryStage);
        });
        mainController.setLoseSwitcher(() -> {
            switchToRoot(scene, loseRoot, primaryStage);
            stop();
        });
        storeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        winController.setWinMenu(() -> {
            System.exit(0);
        });
        loseController.setWinMenu(() -> {System.exit(0);});

        
        // deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
