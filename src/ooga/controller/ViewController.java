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
import ooga.view.application.menu.InGameMenu;
import ooga.view.gui.StageManager;

public class ViewController implements ViewExternalAPI {
  private Controller myController;

  private Scene myCurrentScene;
  private Pane testPane;
  private Group EntityGroup;
  private PhysicsEngine physicsEngine;

  private static final int groundY = 300;
  private Rectangle testRectangle = new Rectangle(50, 50, Color.AZURE);
  private EntityWrapper entityWrapper;
  private List<EntityWrapper> entityList;
  private List<EntityWrapper> entityBuffer;
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



  public ViewController(Pane pane, StageManager stageManager){

    this.menu = new InGameMenu("TestSandBox");

    currentStage = stageManager;
    testPane = pane;
    EntityGroup = new Group();
    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    testPane.getChildren().add(EntityGroup);
    EntityGroup.getChildren().add(testRectangle);
    EntityGroup.getChildren().add(testGround);

    setUpAnimation();

  }

  private void setUpAnimation() {
    entityList.add(new EntityWrapper("Mario_Fire", null));
    entityWrapper = entityList.get(0);
    EntityGroup.getChildren().add(entityWrapper.getRender());

    entityList.add(new EntityWrapper("Brick", null));
    entityBrick = entityList.get(1);
    EntityGroup.getChildren().add(entityBrick.getRender());

  }
  @Override
  public void updateEntityPosition(int id, double newx, double newy) {

  }

  @Override
  public void removeEntity(int id) {

  }

  @Override
  public void addEntity() {

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

  public void handleReleaseInput (KeyCode code) {
    if (code == KeyCode.D || code == KeyCode.A) {
      xAcceleration = 0;
    }
  }

  public void handleMouseInput(double x, double y) {
    if (menu.getButtons().getResumePressed()) {
      System.out.println("PRESSED");
      unPauseGame();
    }
  }

  public void pauseGame(){
    BoxBlur bb = new BoxBlur();
    EntityGroup.setEffect(bb);
    animation.pause();
    testPane.getChildren().add(menu);
    escCounter++;
  }

  public void unPauseGame(){
    testPane.getChildren().remove(testPane.getChildren().size()-1);
    EntityGroup.setEffect(null);
    animation.play();
    escCounter--;
  }
}
