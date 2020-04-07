package ooga.controller;

import javafx.animation.KeyFrame;
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
import javafx.util.Duration;
import ooga.view.application.menu.InGameMenu;
import ooga.view.application.menu.MenuButtons;

import javax.swing.text.html.parser.Entity;
import ooga.model.PhysicsEngine;
import ooga.view.gui.GameCabinet;


public class TestController implements Controller {

  private Scene myCurrentScene;
  private Pane testPane;
  private Group EntityList;
  private PhysicsEngine physicsEngine;

  private static final int groundY = 300;
  private Rectangle testRectangle = new Rectangle(50, 50, Color.AZURE);
  private EntityWrapper entityWrapper;
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



  public TestController (Pane pane, Scene testScene, Stage stage){
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    currentStage = stage;
    testPane = pane;
    EntityList = new Group();
    testPane.getChildren().add(EntityList);
    EntityList.getChildren().add(testRectangle);
    EntityList.getChildren().add(testGround);
    entityWrapper = new EntityWrapper("sampleKeybindings", this);
    EntityList.getChildren().add(entityWrapper.getRender());

    physicsEngine = new PhysicsEngine("dummyString");

    testScene.setOnKeyPressed(e -> {
      handlePressInput(e.getCode());
      entityWrapper.handleKeyInput(e); //FIXME i would like to
    });
    testScene.setOnKeyReleased(e-> {
      entityWrapper.handleKeyReleased(e);
      handleReleaseInput(e.getCode());
    });
    testScene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));

    //TODO: Timeline Code -- don't remove
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }

  private void step (double elapsedTime) {

    physicsEngine.applyForces(entityWrapper.getModel());
    entityWrapper.update(elapsedTime);

    /* potential update code for Entity
    for (EntityWrapper currentEntity : EntityList) {
      for (EntityWrapper targetEntity : EntityList) {
        if(currentEntity.equals(targetEntity)){
          continue;
        }
        else {
          EntityWrapper.update();
        }
      }
    }
     */
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
      EntityList.setEffect(bb);
      animation.pause();
      testPane.getChildren().add(menu);
      escCounter++;
    }
    else if (code == KeyCode.Q && escCounter == 1) {
      testPane.getChildren().remove(testPane.getChildren().size()-1);
      EntityList.setEffect(null);
      animation.play();
      escCounter--;
    }
    else if (code == KeyCode.H) {
      System.out.println("HOME");
      new GameCabinet(currentStage);
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
  public void spawnEntity(String name) {
    
  }
}

