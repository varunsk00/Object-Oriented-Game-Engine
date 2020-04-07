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

public class TestSandboxGreen {
  private static final int SCENE_WIDTH = 1280;
  private static final int SCENE_HEIGHT = 720;
  private Scene myScene;
  private Pane myBackgroundPane;
  private Stage currentStage;
  private Group group;
  private TestController testController;



  public TestSandboxGreen(Stage stage) {
    this.currentStage = stage;
    initModel();
    initView();
    initStage(stage);
    initController();
  }

  private void initModel() {

  }

  private void initView() {
    myBackgroundPane = new Pane();
    BackgroundFill commandBackground = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
    myBackgroundPane.setBackground(new Background(commandBackground));
  }


  private void initController(){
    testController = new TestController(myBackgroundPane, myScene, currentStage);
  }

  private void initStage(Stage primaryStage) {
    myScene = new Scene(myBackgroundPane, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(myScene);
    primaryStage.show();
  }
}
