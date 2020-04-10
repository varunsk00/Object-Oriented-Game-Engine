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
import ooga.model.levels.Camera;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.model.CollisionEngine;

import ooga.view.application.menu.InGameMenu;
import ooga.view.application.menu.MenuButtons;

import javax.swing.text.html.parser.Entity;
import ooga.model.PhysicsEngine;
import ooga.view.gui.GameCabinet;


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
  private Stage currentStage;
  private Scene oldScene;
  private InfiniteLevelBuilder builder;
  private Camera camera;
  private Pane level;




  public TestController (Pane pane, Scene testScene, Stage stage, Scene oldScene) { //FIXME add exception stuff

    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    currentStage = stage;
    this.oldScene = oldScene;
    builder = new InfiniteLevelBuilder(this);

    level = builder.generateLevel();

    testPane = level;
    for(int i = 0; i < 20; i++){
      level.getChildren().add(new Rectangle(0+i*100, 10, 10, 10));
    }
    testScene.setRoot(testPane);

    EntityGroup = new Group();
    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    level.getChildren().add(EntityGroup);
    EntityGroup.getChildren().add(testRectangle);
    EntityGroup.getChildren().add(testGround);
    entityList.add(new EntityWrapper("Mario_Fire", this));


    camera = new Camera(stage, level, entityList.get(0).getRender());
    entityWrapper = entityList.get(0);
    EntityGroup.getChildren().add(entityWrapper.getRender());

//    entityList.add(new EntityWrapper("Brick", this));
//    entityBrick = entityList.get(1);
//    EntityGroup.getChildren().add(entityBrick.getRender());



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

    camera.update();

    builder.updateLevel(camera.getViewPort(), level);
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
      BoxBlur bb = new BoxBlur();
      menu = new InGameMenu("TestSandBox");
      EntityGroup.setEffect(bb);
      animation.pause();
      testPane.getChildren().add(menu);
      escCounter++;
    }
    else if (code == KeyCode.Q && escCounter == 1) {
      testPane.getChildren().remove(testPane.getChildren().size()-1);
      EntityGroup.setEffect(null);
      animation.play();
      escCounter--;
    }
    else if (code == KeyCode.H) {
      System.out.println("HOME");
      currentStage.setScene(oldScene);
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

//  @Override
//  public void spawnEntity(String name) {
//    EntityWrapper newEntity = new EntityWrapper(name, this);
//    entityBuffer.add(newEntity);
//    EntityGroup.getChildren().add(newEntity.getRender());
//  }
}

