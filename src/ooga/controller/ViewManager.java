package ooga.controller;


import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import ooga.apis.view.ViewExternalAPI;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.view.application.Camera;
import ooga.view.application.menu.InGameMenu;
import ooga.view.gui.managers.StageManager;

import java.io.FileNotFoundException;

public class ViewManager implements ViewExternalAPI {
  private Controller myController;

  private Pane testPane;
  private Group EntityGroup;

  private static final int groundY = 300;
  private Rectangle testRectangle = new Rectangle(50, 50, Color.AZURE);

  private Line testGround = new Line(0, groundY, 1000, groundY);
  private InGameMenu menu;
  private int escCounter = 0;

  private StageManager currentStage;
  private InfiniteLevelBuilder builder;
  private Pane level;
  private Camera camera;

  private boolean isGamePaused = false;

  private Scene testScene;

  public ViewManager(StageManager stageManager, InfiniteLevelBuilder builder, Node cameraNode){
    this.menu = new InGameMenu("TestSandBox");
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    currentStage = stageManager;
    this.builder = builder;

    level = builder.generateLevel();

    testPane = level;
    for(int i = 0; i < 20; i++){
      level.getChildren().add(new Rectangle(0+i*100, 10, 10, 10));
    }

    testScene = currentStage.getCurrentScene();
    testScene.setRoot(testPane);

    EntityGroup = new Group();
    level.getChildren().add(EntityGroup);
    EntityGroup.getChildren().add(testRectangle);
    EntityGroup.getChildren().add(testGround);

    this.testScene = stageManager.getCurrentScene();

    this.camera = new Camera(currentStage.getStage(), level, cameraNode);

  }

  public Pane getLevel() {
    return level;
  }

  public void setUpCamera(Node node) {
    camera = new Camera(currentStage.getStage(), level, node);
  }

  public StageManager getCurrentStage() {
    return currentStage;
  }
  public void updateEntityGroup(Node node) {
    EntityGroup.getChildren().add(node);
  }
  public void removeEntityGroup(Node node) {
    EntityGroup.getChildren().remove(node);
  }

  public Scene getTestScene() {
    return testScene;
  }

  public void setLevel(Pane levelBuilt) {
    this.level = levelBuilt;
  }

  public void updateValues() {
    //handleMouseInput();
    camera.update(menu);
    //builder.updateLevel(camera.getViewPort(), level);
  }

  @Override
  public void updateEntityPosition(int id, double newx, double newy) {

  }

  @Override
  public void removeEntity(Node node) {
      EntityGroup.getChildren().remove(node);
  }

  @Override
  public void addEntity(Node node) {
    EntityGroup.getChildren().add(node);
  }

  @Override
  public void updateEntity(int id, String newValue) {

  }

  @Override
  public void setUpGameView(String gameSelect) {

  }

  @Override
  public void checkCollisions() {

  }

  public void step() {

  }

  public void addEntityRender(Node node) {
    EntityGroup.getChildren().add(node);

  }

  public void handlePressInput (KeyCode code) {
    if (code == KeyCode.ESCAPE && escCounter < 1) {
      pauseGame();
    } else if (code == KeyCode.Q && escCounter == 1) {
      unPauseGame();
    }
    else if (code == KeyCode.H) {
      currentStage.updateCurrentScene(currentStage.getCurrentTitle(), currentStage.getCurrentScene());
      currentStage.updateCurrentScene(code.getChar(), currentStage.getPastScene());
      //TODO: pause game when on home screen //pauseGame();
      currentStage.switchScenes("GameSelect");
    }

  }

  public void addScene(String title) {

  }
  public void handleReleaseInput (KeyCode code) {
  }


  public void handleMouseInput() {
    if (menu.getButtons().getResumePressed()) {
      unPauseGame();
      menu.getButtons().setResumeOff();
    }

  }

  public void pauseGame(){
    BoxBlur bb = new BoxBlur();
    EntityGroup.setEffect(bb);
    isGamePaused = true;
    menu.setAlignment(Pos.CENTER);
    testPane.getChildren().add(menu);
    escCounter++;

  }

  public void unPauseGame(){
    testPane.getChildren().remove(menu);
    EntityGroup.setEffect(null);
    isGamePaused = false;
    escCounter--;

  }

  public boolean getIsGamePaused() {
    return isGamePaused;
  }
}
