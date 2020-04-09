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
import ooga.view.gui.StageManager;

import java.io.File;

public class TestSandboxRed extends Game{
    private TestController testController;

    public TestSandboxRed(StageManager sm) {
        super(sm);
    }

    @Override
    public void initModel() {
        gameName = this.getClass().getSimpleName();
    }

    @Override
    public void initView() {
        BackgroundFill commandBackground = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(commandBackground);
    }

    @Override
    protected void initController(){
        this.testController = new TestController(myBackgroundPane, stageManager, oldScene);
    }
}