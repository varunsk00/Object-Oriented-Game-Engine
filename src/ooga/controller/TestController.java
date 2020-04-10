package ooga.controller;

import java.nio.file.attribute.AclEntryType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.model.CollisionEngine;
import ooga.view.application.menu.InGameMenu;
import ooga.view.application.menu.MenuButtons;

import javax.swing.text.html.parser.Entity;
import ooga.model.PhysicsEngine;
import ooga.view.gui.GameCabinet;
import ooga.view.gui.StageManager;


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
  private double xVelocity = 0;
  private double yVelocity = 0;
  private double gravity = 100;
  private double friction = 40;
  private double xAcceleration = 0;
  private boolean isGrounded;
  private boolean keyPressed;
  private Timeline animation;
  private StageManager currentStage;
  private Scene testScene;




  public TestController (Pane pane, StageManager stageManager) { //FIXME add exception stuff
    this.menu = new InGameMenu("TestSandBox");
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    currentStage = stageManager;
    testPane = pane;
    EntityGroup = new Group();
    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    testPane.getChildren().add(EntityGroup);
    EntityGroup.getChildren().add(testRectangle);
    EntityGroup.getChildren().add(testGround);
    entityList.add(new EntityWrapper("Mario_Fire", this));
    entityWrapper = entityList.get(0);
    EntityGroup.getChildren().add(entityWrapper.getRender());

    entityList.add(new EntityWrapper("Brick", this));
    entityBrick = entityList.get(1);
    EntityGroup.getChildren().add(entityBrick.getRender());
    this.testScene = stageManager.getCurrentScene();

    physicsEngine = new PhysicsEngine("dummyString");
    collisionEngine = new CollisionEngine();

    testScene.setOnKeyPressed(e -> {
      handlePressInput(e.getCode());
      for(EntityWrapper entity : entityList){
        entity.handleKeyInput(e);//FIXME i would like to
      }
    });
    testScene.setOnKeyReleased(e-> {
      for(EntityWrapper entity : entityList){
        entity.handleKeyReleased(e);//FIXME i would like to
      }
      handleReleaseInput(e.getCode());
    });
    testScene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));


    setUpTimeline();
  }

  private void setUpTimeline(){
    //TODO: Timeline Code -- don't remove
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step (double elapsedTime) {
    handleMouseInput(0.0, 0.0);
    for(EntityWrapper subjectEntity : entityList){
      for(EntityWrapper targetEntity : entityList){
        collisionEngine.produceCollisionActions(subjectEntity.getModel(), targetEntity.getModel());
      }
      physicsEngine.applyForces(entityWrapper.getModel());
      subjectEntity.update(elapsedTime);
    }
    entityList.addAll(entityBuffer);
    entityBuffer = new ArrayList<>();
  }

  private void handlePressInput (KeyCode code) {
    if (code == KeyCode.D) {
      xAcceleration = 75;
      keyPressed = true;
    } else if (code == KeyCode.A) {
      xAcceleration = -75;
      keyPressed = true;
    }
    else if (code == KeyCode.ESCAPE && escCounter < 1) {
      pauseGame();
    }
    else if (code == KeyCode.Q && escCounter == 1) {
      unPauseGame();
    }
    else if (code == KeyCode.H) {
      System.out.println("HOME");
      currentStage.switchScenes(currentStage.getPastScene());
    }
    if (code == KeyCode.SPACE && isGrounded) {
      yVelocity = -200;
      isGrounded = false;
    }
  }

  private void handleReleaseInput (KeyCode code) {
    if (code == KeyCode.D || code == KeyCode.A) {
      xAcceleration = 0;
    }
  }

  private void handleMouseInput(double x, double y) {
    if (menu.getButtons().getResumePressed()) {
      System.out.println("PRESSED");
      unPauseGame();
    }
  }

  private void pauseGame(){
    BoxBlur bb = new BoxBlur();
    EntityGroup.setEffect(bb);
    animation.pause();
    testPane.getChildren().add(menu);
    escCounter++;
  }

  private void unPauseGame(){
    testPane.getChildren().remove(testPane.getChildren().size()-1);
    EntityGroup.setEffect(null);
    animation.play();
    escCounter--;
  }

  @Override
  public void addEntity(EntityWrapper newEntity) {
    entityBuffer.add(newEntity);
    EntityGroup.getChildren().add(newEntity.getRender());
  }

//  @Override
//  public void spawnEntity(String name) {
//    EntityWrapper newEntity = new EntityWrapper(name, this);
//    entityBuffer.add(newEntity);
//    EntityGroup.getChildren().add(newEntity.getRender());
//  }
}

