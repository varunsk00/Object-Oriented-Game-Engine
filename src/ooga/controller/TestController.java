package ooga.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
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


  private double xVelocity = 0;
  private double yVelocity = 0;
  private double gravity = 100;
  private boolean isGrounded;


  public TestController(Pane pane, Scene testScene) throws Exception {
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    testPane = pane;
    EntityList = new Group();
    testPane.getChildren().add(EntityList);
    EntityList.getChildren().add(testRectangle);
    EntityList.getChildren().add(testGround);
    entityWrapper = new EntityWrapper("sampleKeybindings");
    EntityList.getChildren().add(entityWrapper.getRender());

    testScene.setOnKeyPressed(e -> {
      handleKeyInput(e.getCode());
      entityWrapper.handleKeyInput(e); //FIXME i would like to
    });
    testScene.setOnKeyReleased(e-> entityWrapper.handleKeyReleased());
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
    if(testRectangle.getY() < groundY - testRectangle.getHeight()) {
      yVelocity += gravity * elapsedTime;
    }
    else{
      isGrounded = true;
    }
    if (isGrounded){
      //System.out.println(yVelocity);
      yVelocity = 0;
      testRectangle.setY(groundY - testRectangle.getHeight());
    }
    entityWrapper.update();

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

  private void handleKeyInput (KeyCode code) {
    if (code == KeyCode.SPACE && isGrounded) {
      yVelocity = -200;
      isGrounded = false;
    } else if (code == KeyCode.D) {
      xVelocity = 100;
    } else if (code == KeyCode.A) {
      xVelocity = -100;
    }
  }

  private void handleMouseInput(double x, double y) {
  }
}

