package ooga.controller;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputDevice;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
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



  public FiniteLevelController(StageManager stageManager) { //FIXME add exception stuff

    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    builder = new InfiniteLevelBuilder(this);


    myViewManager = new ViewManager(stageManager, builder, null);
    GameParser hee = new GameParser("SampleLevel", this);
    List<EntityWrapper> je = hee.parseTileEntities();


    entityList = new ArrayList<>();
    entityBrickList = new ArrayList<>();
    entityBuffer = new ArrayList<>();
    //myViewManager.setUpCamera(entityList.get(0).getRender());

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

    GameParser parser = new GameParser("SampleLevel", this);
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
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
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
    for (XInputDevice device : XInputDevice.getAllDevices()) {
      if (device.poll()) {
        // controller is connected, implement your input handling logic here, e.g.:
        XInputComponents components = device.getComponents();
        XInputButtons buttons = components.getButtons();
        boolean bRight = buttons.right; // etc ....
        XInputAxes axes = components.getAxes();
        double xPos = axes.lx; // ou rawx ....
        double yPos = axes.ly;
        int dp = axes.dpad;
        System.out.println(xPos);

        // use device.getPlayerNum() if you need to know which player this device is associated with
      } else {
        // controller is not connected
        // in this situation games typically ask the player to reconnect the controller and pause if possible
      }
    }
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

