package ooga.controller;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;


import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.swing.text.html.parser.Entity;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.util.GamePadListener;


import ooga.model.levels.LevelSelector;
import ooga.util.GameParser;

import ooga.exceptions.ParameterMissingException;
import ooga.view.gui.managers.StageManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameController {

  //  private PhysicsEngine physicsEngine;
//  private CollisionEngine collisionEngine;
  private List<EntityWrapper> entityList;
  private List<EntityWrapper> entitySpawnBuffer;
  private List<EntityWrapper> entityDespawnBuffer;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private final String LOSS_RESULT = "You Lost! Restart the Level by resuming or choose a new game by restarting the game. Thanks for playing!";

  private int nextLevel;

  private Timeline animation;
  private ViewManager myViewManager;
  private ModelManager myModelManager;
  private LevelSelector levelSelector;
  private GamePadListener g;
  private GameParser gameParser;
  private List<EntityWrapper> playerList;

  public GameController(StageManager stageManager, String gameName, boolean loadedGame)
          throws XInputNotLoadedException { //FIXME add exception stuff

    g = new GamePadListener();

    g = new GamePadListener();
    gameParser = new GameParser(gameName, this, loadedGame);
    myViewManager = new ViewManager(stageManager, gameParser.getPlayerList());
    myModelManager = new ModelManager(gameParser);

    entityList = new ArrayList<>();
    entitySpawnBuffer = new ArrayList<>();
    entityDespawnBuffer = new ArrayList<>();
    playerList = gameParser.getPlayerList();

    for (EntityWrapper player : playerList) {
      entityList.add(player);
      myViewManager.addEntity(player.getRender());
    }

    setUpKeyInputs();

    myViewManager.setUpCamera(gameParser.getPlayerList(),
        gameParser.parseGameStatusProfile().readScrollingStatusX(),
        gameParser.parseGameStatusProfile().readScrollingStatusY());
    levelSelector = new LevelSelector(gameParser.parseLevels(), playerList,
        gameParser.parseGameStatusProfile(), myViewManager.getCamera());
    setUpTimeline();

  }

  public List<EntityWrapper> getEntityList() {
    return entityList;
  }

  private void setUpKeyInputs() {
    myViewManager.getTestScene().setOnKeyPressed(e -> {
      myViewManager.handlePressInput(e.getCode());
      for (EntityWrapper entity : entityList) {
        entity.handleKeyInput(e.getCode().toString());
      }
    });
    myViewManager.getTestScene().setOnKeyReleased(e -> {
      for (EntityWrapper entity : entityList) {
        entity.handleKeyReleased(e.getCode().toString());
      }
    });
  }

  private void setUpTimeline() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e ->
    {
      try {
        step(SECOND_DELAY); }
      catch (XInputNotLoadedException ex) {
        new InvalidControlSchemeException(ex); }
      catch (Exception exception) {
        new ParameterMissingException(exception, "resourceFile"); } });
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }

  private void step(double elapsedTime) throws Exception {
    g.update();
    myViewManager.handleMenuInput();
    handleGamePadPlayer();
    if (!myViewManager.getIsGamePaused()) {
      levelSelector.updateCurrentLevel(entityList, entityDespawnBuffer);
      this.handleSaveGame();
      myViewManager.updateCamera();
      myViewManager.updateEntityRenders(entityList, entityDespawnBuffer);
      applyActions(elapsedTime);

      for (EntityWrapper entity : entityList){
        System.out.println(entity.getModel().getX());
      }

      entityList.addAll(entitySpawnBuffer);
      entityList.removeAll(entityDespawnBuffer);
      this.resetEntityBufferLists();
    }
//    entityList.addAll(entitySpawnBuffer);
//    this.resetEntityBufferLists();
  }

  public void removeEntity(EntityWrapper node) {
    entityDespawnBuffer.add(node);
  }

  public void addEntity(EntityWrapper newEntity) {
    entitySpawnBuffer.add(newEntity);
  }

  private void resetEntityBufferLists(){
    entitySpawnBuffer = new ArrayList<>();
    entityDespawnBuffer = new ArrayList<>();

  }

  private void handleGamePadPlayer() {
    if (gameParser.getPlayerList().size() > 1) { //FIXME: TESTCODE FOR CONTROLLER EVENTUALLY SUPPORT SIMUL CONTROLSCHEMES
      if (g.getState() != null) {
        if (!g.getState().getPressed()) {
          gameParser.getPlayerList().get(1).handleControllerInputPressed(g.getState().getControl());
        } else if (g.getState().getPressed()) {
          gameParser.getPlayerList().get(1)
              .handleControllerInputReleased(g.getState().getControl());
        }
      }
    }
  }

  private void applyActions(double elapsedTime) {
    for (EntityWrapper subjectEntity : entityList) {
      for (EntityWrapper targetEntity : entityList) {
        if (!entityDespawnBuffer.contains(targetEntity)) {
          myModelManager.produceCollisions(subjectEntity, targetEntity);
//          myModelManager.getCollisionEngine().produceCollisionActions(subjectEntity.getModel(), targetEntity.getModel());
        }
      }
      subjectEntity.update(elapsedTime);
      myModelManager.applyEntityPhysics(subjectEntity);
    }
    checkIfResetLevel();
  }

  private void checkIfResetLevel() {
    for (EntityWrapper player : gameParser.getPlayerList()) {
      if (myModelManager.checkHealthGone(player)) {
        myViewManager.updateMenu(LOSS_RESULT);
        myViewManager.pauseGame();
        levelSelector.resetLevel(entityList, entityDespawnBuffer);
        return;
      }
    }
  }

    private void handleSaveGame(){
      if (myViewManager.getSaveGame()) {
        JSONArray saveGame = new JSONArray();
        JSONObject obj = new JSONObject();
        for (int i = 0; i < levelSelector.getParsedLevels().size(); i++) {
          obj.put("Level_" + (i + 1), levelSelector.getParsedLevels().get(i).getLevelName());
        }
        saveGame.add(obj);
        gameParser.saveGame("levelArrangement", saveGame);
        myViewManager.setSaveGame();
      }
    }

  public void changeLevel(int levelIndex, EntityWrapper player) {levelSelector.changeCurrentLevel(levelIndex, player); }
}

