package unsw.loopmania;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BattleSceneController {
    @FXML
    private GridPane squares;

    private LoopManiaWorld world;

    private MenuSwitcher gameSwitcher;

    public BattleSceneController(LoopManiaWorld world) {
        this.world = world;
    }

    public void startScene() {
        squares.getChildren().clear();
        List<String> describs = world.getBattleStatus();
        int rowNum = 0;

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        }
        catch (InterruptedException e) {
            return;
        }

        for (String des: describs) {

            Text m = new Text(des); 

            if (des.equals("Battle Starts!\n")) {
                m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                m.setFill(Color.BLUE);
            }
            else if (des.equals("It's your turn!\n")) {
                m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                m.setFill(Color.RED);
            }
            else if (des.equals("It's enemy's turn!\n")) {
                m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                m.setFill(Color.PURPLE);
            }
            else if (des.equals("Too much damage suffered, you have been killed!\n")) {
                m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                m.setFill(Color.BLACK);
            }
            else if (describs.indexOf(des) == describs.size()-1) {
                m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                m.setFill(Color.GREEN);
            }
            else if (des.equals("No battle enemies any more, Tranced enemy will be killed!/n")) {
                m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                m.setFill(Color.ROSYBROWN);
            }

            squares.add(m, 0, rowNum);
            rowNum += 1;

        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case B:
                switchToGame();
                break;
            default:
                break;
        }
    }

    public void switchToGame() {
        gameSwitcher.switchMenu();
    }

    public void setGameSwitcher(MenuSwitcher switcher) {
        gameSwitcher = switcher;
    }


    
}
