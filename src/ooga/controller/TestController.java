package ooga.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ooga.view.application.Camera;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.model.CollisionEngine;

import ooga.view.application.menu.InGameMenu;

import ooga.model.PhysicsEngine;
import ooga.view.gui.managers.StageManager;


public class TestController implements Controller {

  private Scene myCurrentScene;
  private Pane testPane;
  private Group EntityGroup;
  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;


  private static final int groundY = 300;
  private Rectangle testRectangle = new Rectangle(50, 50, Color.AZURE);
  private EntityWrapper entityWrapper;
  private EntityWrapper entityBrick;
  private List<EntityWrapper> entityList;
  private List<EntityWrapper> entityBuffer;
  private Line testGround = new Line(0, groundY, 1000, groundY);
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private InGameMenu menu;
  private int escCounter = 0;
  private Timeline animation;
  private StageManager currentStage;
  private InfiniteLevelBuilder builder;
  private Camera camera;
  private Pane level;

  private Scene testScene;
  private boolean isGamePaused;


  public TestController (StageManager stageManager) { //FIXME add exception stuff

    this.menu = new InGameMenu("TestSandBox");
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    currentStage = stageManager;
    builder = new InfiniteLevelBuilder(this);

    level = builder.generateLevel();

    testPane = level;
    for(int i = 0; i < 20; i++){
      level.getChildren().add(new Rectangle(0+i*100, 10, 10, 10));
    }

    testScene = currentStage.getCurrentScene();
    testScene.setRoot(testPane);

    EntityGroup = new Group();
    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    level.getChildren().add(EntityGroup);
    EntityGroup.getChildren().add(testRectangle);
    EntityGroup.getChildren().add(testGround);
    entityList.add(new EntityWrapper("Mario_Fire", this));


    camera = new Camera(currentStage.getStage(), level, entityList.get(0).getRender());
    entityWrapper = entityList.get(0);
    EntityGroup.getChildren().add(entityWrapper.getRender());
    this.testScene = stageManager.getCurrentScene();

    physicsEngine = new PhysicsEngine("dummyString");
    collisionEngine = new CollisionEngine();

    testScene.setOnKeyPressed(e -> {

      handlePressInput(e.getCode());
      for (EntityWrapper entity : entityList) {
        entity.handleKeyInput(e);//FIXME i would like to
      }
    });
    testScene.setOnKeyReleased(e -> {
      for (EntityWrapper entity : entityList) {
        entity.handleKeyReleased(e);//FIXME i would like to
      }
    });


    setUpTimeline();
  }

  private void setUpTimeline() {
    //TODO: Timeline Code -- don't remove
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step(double elapsedTime) {
    handleMouseInput();
    if (!isGamePaused) {
      camera.update();

      builder.updateLevel(camera.getViewPort(), level);

      for (EntityWrapper subjectEntity : entityList) {
        for (EntityWrapper targetEntity : entityList) {
          collisionEngine.produceCollisionActions(subjectEntity.getModel(), targetEntity.getModel());
        }
        physicsEngine.applyForces(entityWrapper.getModel());
        subjectEntity.update(elapsedTime);
      }
      entityList.addAll(entityBuffer);
      entityBuffer = new ArrayList<>();
    }
  }

  private void handlePressInput(KeyCode code) {
    if (code == KeyCode.ESCAPE && escCounter < 1) {
      pauseGame();
    } else if (code == KeyCode.Q && escCounter == 1) {
      unPauseGame();
    } else if (code == KeyCode.H) {
      currentStage.switchScenes(currentStage.getPastScene());
    }
  }

  private void handleMouseInput() {
    if (menu.getButtons().getResumePressed()) {
      unPauseGame();
      menu.getButtons().setResumeOff();
    }
  }

  private void pauseGame() {
    BoxBlur bb = new BoxBlur();
    EntityGroup.setEffect(bb);
    isGamePaused = true;
    testPane.getChildren().add(menu);
    escCounter++;
  }

  private void unPauseGame() {
    testPane.getChildren().remove(menu);
    EntityGroup.setEffect(null);
    isGamePaused = false;
    escCounter--;
  }

  @Override
  public void addEntity(EntityWrapper newEntity) {
    entityBuffer.add(newEntity);
    EntityGroup.getChildren().add(newEntity.getRender());
  }

  @Override
  public List<EntityWrapper> getEntityList() {
    return entityList;
  }
}