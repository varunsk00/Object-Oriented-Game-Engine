package ooga.view.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameCabinet extends Pane {
    private GameSelectionMenu gameSelectionMenu;
    private List<GamePreview> myGames;
    private AVManager audioVideoManager;
    private StageManager stageManager;

    public GameCabinet(StageManager stageManager, AVManager av) throws Exception { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        this.audioVideoManager = av;
        this.stageManager = stageManager;
        initGameSelect();
        gameSelectionMenu = new GameSelectionMenu(myGames);
        this.getChildren().add(gameSelectionMenu);
        this.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        updateCurrentGame();
    }

    public void addNewGamePreview(GamePreview newGamePreview) {
        this.myGames.add(newGamePreview);
    }

    private void initGameSelect() throws FileNotFoundException { //FIXME: STREAMLINE INSTANTIATION TO READ FROM A FILE
        GamePreview g1 = new GamePreview(Color.BLUE, "metriod");
        GamePreview g2 = new GamePreview(Color.RED, "mario1-1");
        GamePreview g3 = new GamePreview(Color.GREEN, "zelda2");
        GamePreview g4 = new GamePreview(Color.YELLOW, "flappybird");
        GamePreview g5 = new GamePreview(Color.ORANGE, "varun");
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
        if (code == KeyCode.D) {
            this.gameSelectionMenu.scrollRight();
        }
        else if (code == KeyCode.A) {
            this.gameSelectionMenu.scrollLeft();
        }
    }

    public void updateCurrentGame() throws Exception {
        for(GamePreview game: myGames){
            if(game.getGamePressed()) {
                game.resetGameName();
                String gameName = game.getGameName();

                audioVideoManager.switchStage(stageManager, gameName);
                audioVideoManager.switchMusic(stageManager);

            }
        }
    }
}
