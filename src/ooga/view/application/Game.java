package ooga.view.application;

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

public abstract class Game {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    protected Scene myScene;
    protected Pane myBackgroundPane;
    protected Stage currentStage;
    protected Scene oldScene;


    public Game(Stage stage) {
        this.currentStage = stage;
        this.myBackgroundPane = new Pane();
        oldScene = currentStage.getScene();
        initModel();
        initView();
        initStage(stage);
        initController();
    }

    protected abstract void initModel();

    protected abstract void initView();

    public void setBackground(BackgroundFill bg){
        myBackgroundPane.setBackground(new Background(bg));
    }

    protected abstract void initController();

    private void initStage(Stage primaryStage) {
        myScene = new Scene(myBackgroundPane, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(myScene);
    }
}