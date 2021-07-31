package unsw.loopmania;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AcademyController {
    
    @FXML
    private GridPane squares;

    private Image blackImage;
    private Image rageImage;
    private Image healthImage;
    private Image missImage;

    private MenuSwitcher gameSwitcher;

    private LoopManiaWorld world;

    public void setGameSwitcher(MenuSwitcher switcher) {
        gameSwitcher = switcher;
    }

    @FXML
    private void switchToGame() {
        gameSwitcher.switchMenu();
    }

    public AcademyController(LoopManiaWorld world) {
        this.world = world;

        blackImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        rageImage = new Image((new File("src/images/rage.png")).toURI().toString());
        healthImage = new Image((new File("src/images/health.png")).toURI().toString());
        missImage = new Image((new File("src/images/miss.png")).toURI().toString());
    }

    @FXML
    public void initialize() {
        Text techP = new Text("0");
        techP.textProperty().bind(world.getCharacter().getTechPoints().asString());
        squares.add(techP, 1, 0);

        Text attackP = new Text("0");
        attackP.textProperty().bind(world.getCharacter().getAttackPoints().asString());
        squares.add(attackP, 1, 1);

        Text healthP = new Text("0");
        healthP.textProperty().bind(world.getCharacter().getHealthPoints().asString());
        squares.add(healthP, 1, 2);

        Text defenceP = new Text("0");
        defenceP.textProperty().bind(world.getCharacter().getDefencePoints().asString());
        squares.add(defenceP, 1, 3);

        if (world.getCharacter().getAttackPoints().get() >= 10) {
            ImageView image = new ImageView(rageImage);
            squares.add(image, 3, 1);
        }
        else {
            ImageView image = new ImageView(blackImage);
            squares.add(image, 3, 1);
        }

        if (world.getCharacter().getHealthPoints().get() >= 10) {
            ImageView image = new ImageView(healthImage);
            squares.add(image, 3, 2);
        }
        else {
            ImageView image = new ImageView(blackImage);
            squares.add(image, 3, 2);
        }

        if (world.getCharacter().getDefencePoints().get() >= 10) {
            ImageView image = new ImageView(missImage);
            squares.add(image, 3, 3);
        }
        else {
            ImageView image = new ImageView(blackImage);
            squares.add(image, 3, 3);
        }
        


    }




}
