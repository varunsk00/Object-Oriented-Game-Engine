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
import javafx.util.Duration;
import ooga.view.application.menu.InGameMenu;
import ooga.view.application.menu.MenuButtons;

import javax.swing.text.html.parser.Entity;


public class TestController {

  private Scene myCurrentScene;
  private Pane testPane;
  private Group EntityList;

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



  public TestController(Pane pane, Scene testScene){
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    testPane = pane;
    EntityList = new Group();
    testPane.getChildren().add(EntityList);
    EntityList.getChildren().add(testRectangle);
    EntityList.getChildren().add(testGround);
    entityWrapper = new EntityWrapper("sampleKeybindings");
    EntityList.getChildren().add(entityWrapper.getRender());

    testScene.setOnKeyPressed(e -> {
      handlePressInput(e.getCode());
      entityWrapper.handleKeyInput(e); //FIXME i would like to
    });
    testScene.setOnKeyReleased(e-> {
      entityWrapper.handleKeyReleased();
      handleReleaseInput(e.getCode());
    });
    testScene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));

    //TODO: Timeline Code -- don't remove
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }

  private void step (double elapsedTime) {
    testRectangle.setX(testRectangle.getX() + xVelocity * elapsedTime * 1);
    testRectangle.setY(testRectangle.getY() + yVelocity * elapsedTime * 1);

//    applyGravity(elapsedTime);
//    applyAcceleration(elapsedTime);
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
  private void applyGravity(double elapsedTime){
    if(testRectangle.getY() < groundY - testRectangle.getHeight()) {
      yVelocity += gravity * elapsedTime;
    }
    else{
      isGrounded = true;
    }
    if (isGrounded){
      yVelocity = 0;
      testRectangle.setY(groundY - testRectangle.getHeight());
    }
  }

  private void applyAcceleration(double elapsedTime){
    if(Math.abs(xVelocity) < 100) {
      xVelocity += xAcceleration * elapsedTime;
    }
    if(Math.abs(xVelocity) > 0) {
      xVelocity += -Math.signum(xVelocity) * friction * elapsedTime;
    }
    //System.out.println(xVelocity);
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
      testPane.getChildren().add(menu);
      escCounter++;
    }
    else if (code == KeyCode.Q && escCounter == 1) {
      testPane.getChildren().remove(testPane.getChildren().size()-1);
      EntityList.setEffect(null);
      escCounter--;
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
}

