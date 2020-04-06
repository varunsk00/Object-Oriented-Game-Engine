package ooga.view.application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.controller.TestController;
import ooga.view.gui.GamePreview;
import ooga.view.gui.GameSelectionMenu;

public class TestSandbox {

  //TODO: This is a TestSandbox for beginning testing purposes -- taken from Frank's Slogo program, so feel free to change because this is really bad code



  private static final int SCENE_WIDTH = 1000;
  private static final int SCENE_HEIGHT = 600;
  private Scene myScene;
  private Pane myBackgroundPane;
  private BorderPane myMainFrame;
  private Rectangle r1 = new Rectangle(200, 275, 100, 100);
  private Rectangle r2 = new Rectangle(450, 275, 100, 100);
  private Rectangle r3 = new Rectangle(700, 275, 100, 100);
  private List<GamePreview> l;
  private GameSelectionMenu gameSelectionMenu;
  private Group group;
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
    myMainFrame = new BorderPane();
    myBackgroundPane = new Pane();
    GamePreview g1 = new GamePreview(Color.BLUE);
    GamePreview g2 = new GamePreview(Color.RED);
    GamePreview g3 = new GamePreview(Color.YELLOW);
    l = new ArrayList();
    l.add(g1);
    l.add(g2);
    l.add(g3);
    gameSelectionMenu = new GameSelectionMenu(l);
   // myMainFrame.setCenter(myBackgroundPane);
    r1.setFill(Color.BLUE);
    r2.setFill(Color.RED);
    r3.setFill(Color.YELLOW);
   // myBackgroundPane.getChildren().add(r1);
   // myBackgroundPane.getChildren().add(r2);
   // myBackgroundPane.getChildren().add(r3);
    myMainFrame.setCenter(gameSelectionMenu);
    //myBackgroundPane.getChildren().add(rightScrollArrow);
    //myBackgroundPane.getChildren().add(leftScrollArrow);
    BackgroundFill commandBackground = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
    myBackgroundPane.setBackground(new Background(commandBackground));
  }

  private void initController(){
    testController = new TestController(myBackgroundPane, myScene);
  }

  private void initStage(Stage primaryStage) {
    myScene = new Scene(myMainFrame, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(myScene);
    primaryStage.show();
  }

}
