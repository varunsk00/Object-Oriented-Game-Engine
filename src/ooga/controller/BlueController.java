package ooga.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ooga.model.CollisionEngine;
import ooga.model.PhysicsEngine;
import ooga.model.levels.InfiniteLevel;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.model.levels.Level;
import ooga.util.GameParser;
import ooga.util.InfiniteGameParser;
import ooga.view.application.Camera;
import ooga.view.gui.managers.StageManager;

import java.util.ArrayList;
import java.util.List;


public class BlueController implements Controller {

  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;
  private EntityWrapper entityWrapper;
  private List<EntityWrapper> entityList;
  private List<EntityWrapper> entityBuffer;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private int escCounter = 0;
  private Timeline animation;
  private InfiniteLevelBuilder builder;
  private Camera camera;
  private Pane level;
  private ViewManager myViewManager;
  private InfiniteLevel testLevel;


  public BlueController(StageManager stageManager) { //FIXME add exception stuff

    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    builder = new InfiniteLevelBuilder(this);
    level = builder.generateLevel();
    myViewManager = new ViewManager(stageManager, builder);

    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
//    entityList.add(new EntityWrapper("Flappy_Bird", this));
//
//    entityWrapper = entityList.get(0);
//    myViewManager.updateEntityGroup(entityWrapper.getRender());

    physicsEngine = new PhysicsEngine("dummyString");
    collisionEngine = new CollisionEngine();

    myViewManager.getTestScene().setOnKeyPressed(e -> {

      myViewManager.handlePressInput(e.getCode());
      for (EntityWrapper entity : entityList) {
        entity.handleKeyInput(e.getCode().toString());//FIXME i would like to
      }
    });
    myViewManager.getTestScene().setOnKeyReleased(e -> {
      for (EntityWrapper entity : entityList) {
        entity.handleKeyReleased(e.getCode().toString());//FIXME i would like to
      }
    });

    setUpTimeline();
    GameParser parser = new GameParser("Pipe1", this);
    List<EntityWrapper> tiles = parser.parseTileEntities();
    List<EntityWrapper> player = parser.parsePlayerEntities();
    List<EntityWrapper> enemy = parser.parseEnemyEntities();
    for (EntityWrapper k : player) {
      entityList.add(k);
      myViewManager.updateEntityGroup(k.getRender());
    }
    myViewManager.setUpCamera(entityList.get(0).getRender()); //FIXME to be more generalized and done instantly
    testLevel = new InfiniteLevel(tiles, player, enemy);
//    myViewManager.setUpCamera(entityList.get(0).getRender());

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
    if (!myViewManager.getIsGamePaused()) {
      testLevel.despawnEntities(entityList, myViewManager);
      testLevel.spawnEntities(entityList, myViewManager);
      myViewManager.updateValues();
      for (EntityWrapper subjectEntity : entityList) {
        for (EntityWrapper targetEntity : entityList) {
          collisionEngine
              .produceCollisionActions(subjectEntity.getModel(), targetEntity.getModel());
        }
        subjectEntity.update(elapsedTime);
        physicsEngine.applyForces(subjectEntity.getModel());
      }
    }
    entityList.addAll(entityBuffer);
    entityBuffer = new ArrayList<>();
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