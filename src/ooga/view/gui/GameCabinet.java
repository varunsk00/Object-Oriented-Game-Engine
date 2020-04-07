package ooga.view.gui;
import ooga.view.application.TestSandboxGreen;
import ooga.view.application.TestSandboxBlue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.view.application.TestSandboxRed;

import java.util.ArrayList;
import java.util.List;

public class GameCabinet {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private GameSelectionMenu myGameCabinet;
    private List<GamePreview> myGames;
    private Scene myScene;
    private BorderPane mainFrame = new BorderPane();

    public GameCabinet(Stage primaryStage) { //FIXME ADD ERROR HANDLING
        initGameSelect();
        mainFrame.setCenter(myGameCabinet);
        initStage(primaryStage);
        updateCurrentGame(primaryStage);
    }

    public GameSelectionMenu getLibrary(){
        return myGameCabinet;
    }

    private void initGameSelect(){ //FIXME: STREAMLINE INSTANTIATION TO READ FROM A FILE
        this.myGames = new ArrayList<>();
        GamePreview g1 = new GamePreview(Color.BLUE);
        GamePreview g2 = new GamePreview(Color.RED);
        GamePreview g3 = new GamePreview(Color.GREEN);
        myGames.add(g1);
        myGames.add(g2);
        myGames.add(g3);
        this.myGameCabinet = new GameSelectionMenu(myGames);
    }

    private void initStage(Stage primaryStage) {
        myScene = new Scene(mainFrame, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public void updateCurrentGame(Stage myStage) { //FIXME: STREAMLINE GAME CHECKING FROM FILE OR REFLECTIONS ONCE COLORS REPLACED WITH GAME NAME
        for(GamePreview game: myGames){
            if(game.getGamePressed() != null) {
                if(game.getGamePressed().equals("0x008000ff")) {
                    game.resetGameName();
                    new TestSandboxGreen(myStage);
                    myStage.setTitle("TestSandboxGreen");
                }
                else if(game.getGamePressed().equals("0x0000ffff")) {
                    game.resetGameName();
                    new TestSandboxBlue(myStage);
                    myStage.setTitle("TestSandboxBlue");
                }
                else if(game.getGamePressed().equals("0xff0000ff")) {
                    game.resetGameName();
                    new TestSandboxRed(myStage);
                    myStage.setTitle("TestSandboxRed");
                }
            }
        }
    }
}
