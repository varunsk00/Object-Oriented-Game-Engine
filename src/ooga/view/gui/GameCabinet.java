package ooga.view.gui;

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

    public GameCabinet(StageManager stageManager) { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        initGameSelect();
        gameSelectionMenu = new GameSelectionMenu(myGames);
        this.getChildren().add(gameSelectionMenu);

        updateCurrentGame(stageManager);
    }
//    public void scrollLeft() {
//        gameSelectionMenu.scrollLeft();
//    }
//    public void scrollRight() {
//        gameSelectionMenu.scrollRight();
//    }


    public GameSelectionMenu getLibrary(){
        return gameSelectionMenu;
    }

    public void addNewGamePreview(GamePreview newGamePreview) {
        this.myGames.add(newGamePreview);
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

    public void updateCurrentGame(StageManager stageManager) { //FIXME: STREAMLINE GAME CHECKING FROM FILE OR REFLECTIONS ONCE COLORS REPLACED WITH GAME NAME
        for(GamePreview game: myGames){
            if(game.getGamePressed()) {
                if(game.getGameName().equals("green")) {
//                    game.resetGameName();
//                    stageManager.switchScenes();
//                    new TestSandboxGreen(myStage);
//                    myStage.setTitle("TestSandboxGreen");
//                }
//                else if(game.getGameName().equals("blue")) {
//                    game.resetGameName();
//                    new TestSandboxBlue(myStage);
//                    myStage.setTitle("TestSandboxBlue");
//                }
//                else if(game.getGameName().equals("red")) {
//                    game.resetGameName();
//                    new TestSandboxRed(myStage);
//                    myStage.setTitle("TestSandboxRed");
                }
            }
        }
    }
}
