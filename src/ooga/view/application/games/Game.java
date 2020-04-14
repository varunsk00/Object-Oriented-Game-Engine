package ooga.view.application.games;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import ooga.view.gui.managers.StageManager;

public abstract class Game {
    protected Pane myBackgroundPane;
    protected StageManager stageManager;
    protected Scene oldScene;
    protected String gameName;
    protected Scene currentScene;


    public Game(StageManager stageManager) {
        this.stageManager = stageManager;
        this.oldScene = stageManager.getPastScene();
        this.currentScene = stageManager.getCurrentScene();
        this.myBackgroundPane = new Pane();
        initModel();
        initView();
        initStage();
        initController();
    }

    protected abstract void initModel();

    protected abstract void initView();

    public void setBackground(BackgroundFill bg){
        myBackgroundPane.setBackground(new Background(bg));
    }

    protected abstract void initController();

    private void initStage() {
        stageManager.createAndSwitchScenes(myBackgroundPane, gameName);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(StageManager sm) {
        this.currentScene = sm.getCurrentScene();
    }
}