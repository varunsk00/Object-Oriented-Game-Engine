package ooga.view.application.games;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import ooga.controller.GameController;
import ooga.view.gui.managers.StageManager;

public class Game {
    private Pane myBackgroundPane;
    private StageManager stageManager;
    private Scene currentScene;
    private GameController mainController;

    public Game(StageManager stageManager) {
        this.stageManager = stageManager;
        this.currentScene = stageManager.getCurrentScene();
        this.myBackgroundPane = new Pane();
        initStage();
    }

    public void loadGame(String gameName, boolean loadedGame) throws XInputNotLoadedException {
        stageManager.setCurrentTitle(gameName);
        this.mainController = new GameController(stageManager, gameName, loadedGame);
    }

    private void initStage() {
        stageManager.createAndSwitchScenes(myBackgroundPane);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(StageManager sm) {
        this.currentScene = sm.getCurrentScene();
    }
}
