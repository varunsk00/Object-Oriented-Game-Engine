package ooga.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ooga.model.CollisionEngine;
import ooga.model.PhysicsEngine;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.view.gui.managers.StageManager;

import java.util.ArrayList;
import java.util.List;

public class MainController implements Controller {

  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;
  private EntityWrapper entityWrapper;
  private List<EntityWrapper> entityList;
  private List<EntityWrapper> entityBrickList;
  private List<EntityWrapper> entityBuffer;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private Timeline animation;
  private InfiniteLevelBuilder builder;
  private ViewManager myViewManager;


  public MainController(StageManager stageManager) { //FIXME add exception stuff

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

    for (int i = 0; i < 100; i++) {
      EntityWrapper local = new EntityWrapper("Brick", this);
      local.getModel().setX(i*100);
      local.getModel().setY(400);
      entityBrickList.add(local);
      entityList.add(local);
      myViewManager.updateEntityGroup(local.getRender());
    }
    EntityWrapper local = new EntityWrapper("Brick", this);
    local.getModel().setX(400);
    local.getModel().setY(300);
    entityBrickList.add(local);
    entityList.add(local);
    myViewManager.updateEntityGroup(local.getRender());

    physicsEngine = new PhysicsEngine("dummyString");
    collisionEngine = new CollisionEngine();

    myViewManager.getTestScene().setOnKeyPressed(e -> {

      myViewManager.handlePressInput(e.getCode());
      for(EntityWrapper entity : entityList){
        entity.handleKeyInput(e.getCode().toString());//FIXME i would like to
      }
    });
    myViewManager.getTestScene().setOnKeyReleased(e-> {
      for(EntityWrapper entity : entityList){
        entity.handleKeyReleased(e.getCode().toString());//FIXME i would like to
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
    if (!myViewManager.getIsGamePaused()) {
      myViewManager.updateValues();
      for (EntityWrapper subjectEntity : entityList) {
        for (EntityWrapper targetEntity : entityList) {
          collisionEngine.produceCollisionActions(subjectEntity.getModel(), targetEntity.getModel());
        }
        subjectEntity.update(elapsedTime);
        physicsEngine.applyForces(subjectEntity.getModel());
      }
      entityList.addAll(entityBuffer);
      entityBuffer = new ArrayList<>();
    }
    entityList.addAll(entityBuffer);
    entityBuffer = new ArrayList<>();
  }

  public void removeEntity(EntityWrapper node) {
    myViewManager.removeEntity(node.getRender());
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

