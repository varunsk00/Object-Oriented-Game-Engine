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
import ooga.model.levels.LevelSelecter;
import ooga.util.GameParser;
import ooga.util.InfiniteGameParser;
import ooga.view.application.Camera;
import ooga.view.gui.managers.StageManager;

import java.util.ArrayList;
import java.util.List;


public class InfiniteLevelController implements Controller {


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
  private ooga.model.levels.InfiniteLevel level1;
  private ooga.model.levels.InfiniteLevel level2;
  private ooga.model.levels.InfiniteLevel level3;
  private LevelSelecter levelSelecter;


  public InfiniteLevelController(StageManager stageManager) { //FIXME add exception stuff

    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    builder = new InfiniteLevelBuilder(this);
    level = builder.generateLevel();

    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    entityList.add(new EntityWrapper("Flappy_Bird", this));

    myViewManager = new ViewManager(stageManager, builder, entityList.get(0).getRender()); //FIXME to be more generalized and done instantly

    entityWrapper = entityList.get(0);
    myViewManager.updateEntityGroup(entityWrapper.getRender());


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
    GameParser parser1 = new GameParser("Pipe1", this);
    GameParser parser2 = new GameParser("Pipe2", this);
    GameParser parser3 = new GameParser("Pipe3", this);
    GameParser parser4 = new GameParser("Pipe4", this);
    GameParser parser5 = new GameParser("Pipe5", this);

    List<EntityWrapper> player = new ArrayList<>();
    player.add(entityWrapper);
    level1 = new ooga.model.levels.InfiniteLevel(parser1.parseTileEntities(), player, parser1.parseEnemyEntities());
    level2 = new ooga.model.levels.InfiniteLevel(parser2.parseTileEntities(), player, parser2.parseEnemyEntities());
    level3 = new ooga.model.levels.InfiniteLevel(parser3.parseTileEntities(), player, parser3.parseEnemyEntities());
    ooga.model.levels.InfiniteLevel level4 = new ooga.model.levels.InfiniteLevel(parser4.parseTileEntities(), player, parser4.parseEnemyEntities());
    ooga.model.levels.InfiniteLevel level5 = new ooga.model.levels.InfiniteLevel(parser5.parseTileEntities(), player, parser5.parseEnemyEntities());

    List<ooga.model.levels.InfiniteLevel> levels = new ArrayList<>();
    levels.add(level1);
    levels.add(level2);
    levels.add(level3);
    levels.add(level4);
    levels.add(level5);



    levelSelecter = new LevelSelecter(levels);


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
      levelSelecter.updateCurrentLevel(entityList, myViewManager);
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

  @Override
  public void removeEntity(EntityWrapper node) {
    //FIXME why is this empty? are we combining controllers?
  }
}