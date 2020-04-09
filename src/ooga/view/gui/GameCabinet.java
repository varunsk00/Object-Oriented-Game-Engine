package ooga.view.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameCabinet extends Pane {
    private GameSelectionMenu gameSelectionMenu;
    private List<GamePreview> myGames;
    private AVManager av;

    public GameCabinet(StageManager stageManager) throws Exception { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        this.av = new AVManager();
        initGameSelect();
        gameSelectionMenu = new GameSelectionMenu(myGames);
        this.getChildren().add(gameSelectionMenu);
        this.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        updateCurrentGame(stageManager);
    }

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
        g1.setGameName("TestSandboxBlue");
        g2.setGameName("TestSandboxRed");
        g3.setGameName("TestSandboxGreen");
        g4.setGameName("TestSandboxYellow");
        g5.setGameName("TestSandboxOrange");
        myGames.add(g1);
        myGames.add(g2);
        myGames.add(g3);
        myGames.add(g4);
        myGames.add(g5);
    }

    public void handleAltScrollInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            this.gameSelectionMenu.scrollRight();
        }
        else if (code == KeyCode.LEFT) {
            this.gameSelectionMenu.scrollLeft();
        }
    }

    public void updateCurrentGame(StageManager stageManager) throws Exception {
        for(GamePreview game: myGames){
            if(game.getGamePressed()) {
                game.resetGameName();
                String gameName = game.getGameName();
                av.switchStage(stageManager, gameName);
                av.switchMusic(gameName);
            }
        }
    }
}
