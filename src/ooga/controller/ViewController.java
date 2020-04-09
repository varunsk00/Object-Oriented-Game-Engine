package ooga.controller;


import java.util.List;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.apis.view.ViewExternalAPI;
import ooga.model.PhysicsEngine;
import ooga.view.application.menu.InGameMenu;

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


  public ViewController(Controller controller, Pane pane, Scene testScene, Stage stage, Scene oldScene){
    myController = controller;
    currentStage = stage;
    this.oldScene = oldScene;
    testPane = pane;
    EntityGroup = new Group();
    testPane.getChildren().add(EntityGroup);
    EntityGroup.getChildren().add(testRectangle);
    EntityGroup.getChildren().add(testGround);

    setUpAnimation();
  }

  private void setUpAnimation() {

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

}
