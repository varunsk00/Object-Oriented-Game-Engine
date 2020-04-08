package ooga.view.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.view.application.TestSandboxBlue;
import ooga.view.application.TestSandboxGreen;
import ooga.view.application.TestSandboxRed;

import java.util.ArrayList;
import java.util.List;

public class GameCabinet extends Pane {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private GameSelectionMenu gameSelectionMenu;
    private List<GamePreview> myGames;

    public GameCabinet(Stage primaryStage) { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        initGameSelect();
        gameSelectionMenu = new GameSelectionMenu(myGames);
        this.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        this.getChildren().add(gameSelectionMenu);
        updateCurrentGame(primaryStage);
    }

    public GameSelectionMenu getLibrary(){
        return gameSelectionMenu;
    }

    private void initGameSelect(){ //FIXME: STREAMLINE INSTANTIATION TO READ FROM A FILE
        GamePreview g1 = new GamePreview(Color.BLUE);
        GamePreview g2 = new GamePreview(Color.RED);
        GamePreview g3 = new GamePreview(Color.GREEN);
        GamePreview g4 = new GamePreview(Color.YELLOW);
        GamePreview g5 = new GamePreview(Color.ORANGE);
        g1.setGameName("blue");
        g2.setGameName("red");
        g3.setGameName("green");
        g4.setGameName("yellow");
        g5.setGameName("orange");
        myGames.add(g1);
        myGames.add(g2);
        myGames.add(g3);
        myGames.add(g4);
        myGames.add(g5);

    }
    public void handleAltScrollInput(KeyCode code) {
        System.out.println(code);
        if (code == KeyCode.RIGHT) {
            this.gameSelectionMenu.scrollRight();
        }
        else if (code == KeyCode.LEFT) {
            this.gameSelectionMenu.scrollLeft();
        }

    }



    public void updateCurrentGame(Stage myStage) { //FIXME: STREAMLINE GAME CHECKING FROM FILE OR REFLECTIONS ONCE COLORS REPLACED WITH GAME NAME
        for(GamePreview game: myGames){
            if(game.getGamePressed()) {
                if(game.getGameName().equals("green")) {
                    game.resetGameName();
                    new TestSandboxGreen(myStage);
                    myStage.setTitle("TestSandboxGreen");
                }
                else if(game.getGameName().equals("blue")) {
                    game.resetGameName();
                    new TestSandboxBlue(myStage);
                    myStage.setTitle("TestSandboxBlue");
                }
                else if(game.getGameName().equals("red")) {
                    game.resetGameName();
                    new TestSandboxRed(myStage);
                    myStage.setTitle("TestSandboxRed");
                }
            }
        }
    }
}
