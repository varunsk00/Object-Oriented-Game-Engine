package ooga.view;

import java.awt.Dimension;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.controller.TestController;

public class TestSandbox {

  //TODO: This is a TestSandbox for beginning testing purposes -- taken from Frank's Slogo program, so feel free to change


  private static final int SCENE_WIDTH = 1000;
  private static final int SCENE_HEIGHT = 600;
  private Scene myScene;
  private Pane myBackgroundPane;
  private TestController testController;

  public TestSandbox(Stage stage) {
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
    testController = new TestController(myScene);
  }

  private void initStage(Stage primaryStage) {
    myScene = new Scene(myBackgroundPane, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(myScene);
    primaryStage.show();
  }

}
