package ooga.view.application;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.TestController;

import java.io.File;

public class TestSandboxBlue extends Game{
    private TestController testController;

    public TestSandboxBlue(Stage stage) {
        super(stage);
    }

    @Override
    public void initModel() {

    }

    @Override
    public void initView() {
        BackgroundFill commandBackground = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(commandBackground);
    }

    @Override
    protected void initController() {
        this.testController = new TestController(myBackgroundPane, myScene, currentStage, oldScene);
    }

}
