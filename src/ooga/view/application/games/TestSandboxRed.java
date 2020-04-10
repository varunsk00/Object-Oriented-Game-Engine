package ooga.view.application.games;

import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import ooga.controller.TestController;
import ooga.view.application.games.Game;
import ooga.view.gui.StageManager;

public class TestSandboxRed extends Game {
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