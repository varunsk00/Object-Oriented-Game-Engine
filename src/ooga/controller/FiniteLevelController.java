package ooga.controller;

import java.io.Serializable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.swing.text.html.parser.Entity;
import ooga.model.CollisionEngine;
import ooga.model.PhysicsEngine;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.model.levels.Level;
import ooga.util.GameParser;
import ooga.view.application.games.Game;
import ooga.view.gui.managers.StageManager;

import java.util.ArrayList;
import java.util.List;

public class FiniteLevelController implements Controller {

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
  private Level testLevel;
  private Game currentGame;



  public FiniteLevelController(StageManager stageManager, Game currGame) { //FIXME add exception stuff

    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
//<<<<<<< HEAD
//
//=======
//    builder = new InfiniteLevelBuilder(this);
//
//
//    myViewManager = new ViewManager(stageManager, builder, null);
//>>>>>>> c8147b6db3bebc4253ceb470282bbe059ca37edc

    entityList = new ArrayList<>();
    entityBrickList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    //myViewManager.setUpCamera(entityList.get(0).getRender());

    physicsEngine = new PhysicsEngine("dummyString");
    collisionEngine = new CollisionEngine();

    setUpLevelBuilder(stageManager, currGame);

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

  public void setUpLevelBuilder(StageManager stageManager, Game currGame) {
    builder = new InfiniteLevelBuilder(this);

    myViewManager = new ViewManager(stageManager, builder, null, currGame);
    GameParser hee = new GameParser("MarioLevel", this);
    List<EntityWrapper> je = hee.parseTileEntities();

  //  GameParser parser = new GameParser("SampleLevel", this);
    GameParser parser = new GameParser("MarioLevel", this);
    List<EntityWrapper> tiles = parser.parseTileEntities();
    List<EntityWrapper> player = parser.parsePlayerEntities();
    List<EntityWrapper> enemy = parser.parseEnemyEntities();
    for(EntityWrapper k : player){
      entityList.add(k);
      myViewManager.updateEntityGroup(k.getRender());
    }
    myViewManager.setUpCamera(entityList.get(0).getRender()); //FIXME to be more generalized and done instantly
    testLevel = new Level(tiles, player, enemy);
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
      testLevel.despawnEntities(entityList, myViewManager);
      testLevel.spawnEntities(entityList, myViewManager);
      myViewManager.updateValues();
      //TODO: Consider making one method in Level.java as updateLevel() for the methods above^, although I concern about whether or not spawnEntities would get an up-to-date EntityList
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

