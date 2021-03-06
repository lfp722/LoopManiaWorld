package unsw.loopmania;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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

    private LoopManiaWorldControllerLoader loopManiaLoader;

    private FXMLLoader gameLoader;

    private Parent gameRoot;

    private String path = "2021-08-02T07:56:13.413039.json";

    public void setPath(String path) {
        this.path = path;
    }

    public void setMainController(String path){
        try {
            loopManiaLoader = new LoopManiaWorldControllerLoader(path);
            mainController = loopManiaLoader.loadController();
            gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
            gameLoader.setController(mainController);
            gameRoot = gameLoader.load();
        }
        catch (IOException e) {
            System.out.println("lalala");
            return;
        }

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        StartMenuController startController = new StartMenuController();
        FXMLLoader startLoader = new FXMLLoader(getClass().getResource("StartMenu.fxml"));
        startLoader.setController(startController);
        Parent startMenuRoot = startLoader.load();

        LoadMenuController loadController = new LoadMenuController();
        FXMLLoader loadLoader = new FXMLLoader(getClass().getResource("LoadMenu.fxml"));
        loadLoader.setController(loadController);
        Parent loadMenuRoot = loadLoader.load();


        setMainController(path);


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

        // load lose window then the character dead
        LoseController loseController = new LoseController();
        FXMLLoader loseLoader = new FXMLLoader(getClass().getResource("LoseScene.fxml"));
        loseLoader.setController(loseController);
        Parent loseRoot = loseLoader.load();

        // load exit window then the character dead
        ExitMenuController exitController = new ExitMenuController();
        FXMLLoader exitLoader = new FXMLLoader(getClass().getResource("ExitMenuView.fxml"));
        exitLoader.setController(exitController);
        Parent exitRoot = exitLoader.load();

        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("About.fxml"));
        Parent aboutRoot = aboutLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(startMenuRoot);

        AcademyController academyController = new AcademyController(mainController.getWorld());
        FXMLLoader academyLoader = new FXMLLoader(getClass().getResource("Academy.fxml"));
        academyLoader.setController(academyController);
        Parent academyRoot = academyLoader.load();

        BattleSceneController battleController = new BattleSceneController(mainController.getWorld());
        FXMLLoader battleLoader = new FXMLLoader(getClass().getResource("BattleScene.fxml"));
        battleLoader.setController(battleController);
        Parent battleRoot = battleLoader.load();

        WorldBuildController wbController = new WorldBuildController();
        FXMLLoader wbLoader = new FXMLLoader(getClass().getResource("WorldBuilder.fxml"));
        wbLoader.setController(wbController);
        Parent wbRoot = wbLoader.load();

        

        // Scene scene = new Scene(storeRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        startController.setNewSwitcher(()->{
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });

        startController.setLoadSwitcher(()->{switchToRoot(scene, loadMenuRoot, primaryStage);});

        startController.setAboutSwitcher(()->{switchToRoot(scene, aboutRoot, primaryStage);});

        loadController.setStartSwitcher(()->{switchToRoot(scene, startMenuRoot, primaryStage);});

        loadController.setLoadSwitcher((String path)->{
            JSONObject json = new JSONObject();
            try {
                json = new JSONObject(new JSONTokener(new FileReader(path)));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            String newPath = s + "/worlds/"+json.getString("path");
            System.out.println(newPath);
            setMainController(newPath);
            try {
                mainController.reload(path);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mainController.setMode(json.getInt("mode"));
            if (json.getInt("mode") == LoopManiaWorld.BERSERKER) {
                storeController.setLimitOfOutfit(1);
            }
            else if (json.getInt("mode") == LoopManiaWorld.SURVIVAL) {
                storeController.setLimitOfPotion(1);
            }

            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
            
        });

        startController.setBuilderSwitcher(()->{switchToRoot(scene, wbRoot, primaryStage);});

        wbController.setMenuSwitcher(()->{switchToRoot(scene, startMenuRoot, primaryStage);});

        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, exitRoot, primaryStage);});

        mainController.setBattleSwitcher(() -> {
            switchToRoot(scene, battleRoot, primaryStage);
            battleController.startScene();
        });

        battleController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        mainMenuController.setNormalGameSwitcher(() -> {
            mainController.setMode(LoopManiaWorld.NORMAL);

            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        mainMenuController.setBerserkerGameSwitcher(() -> {
            mainController.setMode(LoopManiaWorld.BERSERKER);

            storeController.setLimitOfOutfit(1);
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        mainMenuController.setSurvivalGameSwitcher(() -> {
            mainController.setMode(LoopManiaWorld.SURVIVAL);

            storeController.setLimitOfPotion(1);
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        mainMenuController.setConfusingGameSwitcher(()->{
            mainController.setMode(LoopManiaWorld.CONFUSING);
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        mainController.setStoreSwitcher(() -> {
            storeController.initializeCounts();
            storeController.initialize();
            mainController.pause();
            switchToRoot(scene, storeRoot, primaryStage);
        });
        mainController.setAcademySwitcher(() -> {
            academyController.initialize();
            mainController.pause();
            switchToRoot(scene, academyRoot, primaryStage);
        });
        academyController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        mainController.setWinSwitcher(() -> {
            switchToRoot(scene, winRoot, primaryStage);
        });
        mainController.setLoseSwitcher(() -> {
            switchToRoot(scene, loseRoot, primaryStage);
        });
        
        exitController.setResumeGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        exitController.setRestartGameSwitcher(() -> {
            mainController.restart();
            switchToRoot(scene, gameRoot, primaryStage);            
            mainController.startTimer();
        });

        exitController.setSaveGameSwitcher(() -> {
            mainController.save();
        });

        storeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        winController.setWinMenu(() -> {
            System.exit(0);
        });

        winController.setRetry(()-> {
            mainController.restart();
            switchToRoot(scene, gameRoot, primaryStage);            
            mainController.startTimer();
        }

        );

        loseController.setWinMenu(() -> {System.exit(0);});

        loseController.setRetry(()-> {
            mainController.restart();
            switchToRoot(scene, gameRoot, primaryStage);            
            mainController.startTimer();
        }

        );

        
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
