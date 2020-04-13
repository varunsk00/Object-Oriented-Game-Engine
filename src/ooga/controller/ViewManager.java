package ooga.controller;


import java.util.ArrayList;
import java.util.List;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.apis.view.ViewExternalAPI;
import ooga.model.PhysicsEngine;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.view.application.Camera;
import ooga.view.application.menu.InGameMenu;
import ooga.view.gui.managers.StageManager;

public class ViewManager implements ViewExternalAPI {
  private Controller myController;

  private Scene myCurrentScene;
  private Pane testPane;
  private Group EntityGroup;
  private PhysicsEngine physicsEngine;

  private static final int groundY = 300;
  private Rectangle testRectangle = new Rectangle(50, 50, Color.AZURE);
  private EntityWrapper entityWrapper;
  private EntityWrapper entityBrick;
  private Line testGround = new Line(0, groundY, 1000, groundY);
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private InGameMenu menu;
  private int escCounter = 0;
  private double xVelocity = 0;
  private double yVelocity = 0;
  private double gravity = 100;
  private double friction = 40;
  private double xAcceleration = 0;
  private boolean isGrounded;
  private boolean keyPressed;
  private Timeline animation;
  private StageManager currentStage;
  private Scene oldScene;
  private InfiniteLevelBuilder builder;
  private Pane level;
  private Camera camera;
  private boolean isGamePaused = false;


  private Scene testScene;

  public ViewManager(StageManager stageManager, InfiniteLevelBuilder builder){
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


  }

  public void setUpCamera(Node node) {
    camera = new Camera(currentStage.getStage(), level, node);

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
    camera.update();
    builder.updateLevel(camera.getViewPort(), level);
  }


//  private void setUpAnimation() {
//    entityList.add(new EntityWrapper("Mario_Fire", null));
//    entityWrapper = entityList.get(0);
//    EntityGroup.getChildren().add(entityWrapper.getRender());
//
//    entityList.add(new EntityWrapper("Brick", null));
//    entityBrick = entityList.get(1);
//    EntityGroup.getChildren().add(entityBrick.getRender());
//
//  }
  @Override
  public void updateEntityPosition(int id, double newx, double newy) {

  }

  @Override
  public void removeEntity(int id) {

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
    } else if (code == KeyCode.H) {
      currentStage.switchScenes(currentStage.getPastScene());
    }

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
