package ooga.view.application.games;

import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import ooga.controller.TestController;
import ooga.view.gui.managers.StageManager;

public class TestSandboxGreen extends Game {
  private TestController testController;

  public TestSandboxGreen(StageManager sm) {
    super(sm);
  }

  @Override
  protected void initModel() {
    gameName = this.getClass().getSimpleName();
  }

  @Override
  protected void initView() {
    BackgroundFill commandBackground = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
    setBackground(commandBackground);
  }

  @Override
  protected void initController(){
    this.testController = new TestController(myBackgroundPane, stageManager);
  }

}