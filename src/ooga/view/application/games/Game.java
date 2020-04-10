package ooga.view.application.games;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.controller.TestController;
import ooga.view.gui.StageManager;

public abstract class Game {
    protected Pane myBackgroundPane;
    protected StageManager stageManager;
    protected Scene oldScene;
    protected String gameName;


    public Game(StageManager stageManager) {
        this.stageManager = stageManager;
        this.oldScene = stageManager.getPastScene();
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
}