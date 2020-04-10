package ooga.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ooga.view.application.Camera;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.model.CollisionEngine;
import ooga.model.actions.SetGroundStatus;

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
  private List<EntityWrapper> entityBrickList;
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
  private ViewManager myViewManager;


  public TestController (StageManager stageManager) { //FIXME add exception stuff

    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    builder = new InfiniteLevelBuilder(this);
    myViewManager = new ViewManager(stageManager, builder);

    entityList = new ArrayList<>();
    entityBrickList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    entityList.add(new EntityWrapper("Mario_Fire", this));

    myViewManager.setUpCamera(entityList.get(0).getRender());

    entityWrapper = entityList.get(0);
    myViewManager.updateEntityGroup(entityWrapper.getRender());

    for (int i = 0; i < 10; i++) {
      EntityWrapper local = new EntityWrapper("Brick", this);
      local.getModel().setX(i*100);
      local.getModel().setY(400);
      entityBrickList.add(local);
      entityList.add(local);
      myViewManager.updateEntityGroup(local.getRender());
    }



    physicsEngine = new PhysicsEngine("dummyString");
    collisionEngine = new CollisionEngine();

    myViewManager.getTestScene().setOnKeyPressed(e -> {

      myViewManager.handlePressInput(e.getCode());
      for(EntityWrapper entity : entityList){
        entity.handleKeyInput(e);//FIXME i would like to
      }
    });
    myViewManager.getTestScene().setOnKeyReleased(e-> {
      for(EntityWrapper entity : entityList){
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

  private void step (double elapsedTime) {
   // myViewManager.updateValues();

    if (!myViewManager.getIsGamePaused()) {
      //camera.update();
      //builder.updateLevel(camera.getViewPort(), level);
      for (EntityWrapper subjectEntity : entityList) {
        for (EntityWrapper targetEntity : entityList) {
          collisionEngine.produceCollisionActions(subjectEntity.getModel(), targetEntity.getModel());
        }
        subjectEntity.update(elapsedTime);
        physicsEngine.applyForces(subjectEntity.getModel());
      }
    }

  }

  @Override
  public void addEntity(EntityWrapper newEntity) {
    entityBuffer.add(newEntity);
    myViewManager.addEntity(newEntity.getRender());
  }

  @Override
  public List<EntityWrapper> getEntityList() {
    return entityList;
  }

}

