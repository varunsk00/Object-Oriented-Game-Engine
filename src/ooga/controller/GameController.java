package ooga.controller;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ooga.model.CollisionEngine;
import ooga.model.PhysicsEngine;
import ooga.model.controlschemes.GamePad;
import ooga.model.levels.FiniteLevel;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.model.levels.Level;
import ooga.model.levels.LevelSelecter;
import ooga.util.GameParser;
import ooga.util.LevelParser;
import ooga.view.gui.managers.StageManager;

public class GameController implements Controller {

  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;
  private EntityWrapper entityWrapper;
  private List<EntityWrapper> entityList;
  private List<EntityWrapper> player;
  private List<EntityWrapper> entityBrickList;
  private List<EntityWrapper> entityBuffer;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private Timeline animation;
  private InfiniteLevelBuilder builder;
  private ViewManager myViewManager;
  private LevelSelecter levelSelecter;
  private GamePad g;
  private GameParser gameParser;



  public GameController(StageManager stageManager, String gameName) throws XInputNotLoadedException { //FIXME add exception stuff
    System.out.println(gameName);
    builder = new InfiniteLevelBuilder(this);
    g = new GamePad();

    myViewManager = new ViewManager(stageManager, builder, null);
    gameParser = new GameParser(gameName, this);

    entityList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    EntityWrapper player = new EntityWrapper("Mario_Fire", this);


    physicsEngine = new PhysicsEngine(gameParser.parsePhysicsProfile()); //TODO: add PhysicsProfile object
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



    entityList.add(player);
    myViewManager.updateEntityGroup(player.getRender());

    myViewManager.setUpCamera(entityList.get(0).getRender()); //FIXME to be more generalized and done instantly


    levelSelecter = new LevelSelecter(gameParser.parseLevels());

    setUpTimeline();

  }

  private void setUpTimeline() {
    //TODO: Timeline Code -- don't remove
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e ->
    {
      try {
        step(SECOND_DELAY);
      } catch (XInputNotLoadedException ex) {
        ex.printStackTrace();
      }
    });
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }

  private void step (double elapsedTime) throws XInputNotLoadedException {
    g.update();
    myViewManager.handleMouseInput();
//    if (player.size() >1 ) { //FIXME: TESTCODE FOR CONTROLLER EVENTUALLY SUPPORT SIMUL CONTROLSCHEMES
//      if (g.getState() != null) {
//        if (!g.getState().getPressed()) {
//          System.out.println("PRESSED");
//          player.get(1).handleControllerInputPressed(g.getState().getControl());
//        } else if (g.getState().getPressed()) {
//          System.out.println("RELEASED");
//          player.get(1).handleControllerInputReleased(g.getState().getControl());
//        }
//      }
//    }
      if (!myViewManager.getIsGamePaused()) {
        levelSelecter.updateCurrentLevel(entityList, myViewManager);
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
