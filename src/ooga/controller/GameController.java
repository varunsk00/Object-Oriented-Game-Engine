package ooga.controller;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;


import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import ooga.model.levels.Level;
import ooga.util.GamePadListener;


import ooga.model.levels.LevelSelector;
import ooga.util.GameParser;

import ooga.view.gui.managers.StageManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameController implements Controller {


  private List<EntityWrapper> entityList;
  private List<EntityWrapper> entityBuffer;
  private List<EntityWrapper> entityRemove;
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
    entityBuffer = new ArrayList<>();
    entityRemove = new ArrayList<>();
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

  @Override
  public void removeEntity(EntityWrapper node) {
    entityRemove.add(node);
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
        step(SECOND_DELAY);
      } catch (XInputNotLoadedException ex) {
        ex.printStackTrace();
      } catch (Exception exception) {
        exception.printStackTrace(); //FIXME: REPLACE TO AVOID FAIL CASE
      }
    });
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
      levelSelector.updateCurrentLevel(entityList, myViewManager, nextLevel);
      handleSaveGame();
      myViewManager.updateValues();
      applyActions(elapsedTime);

      addToEntityList();

      removeEntities(entityRemove);
      nextLevel = entityList.get(0).getModel().getNextLevelIndex();
    }
    addToEntityList();
  }

  private void removeEntities(List<EntityWrapper> entities) {
    for (EntityWrapper despawnedEntity : entities) {
      myViewManager.removeEntity(despawnedEntity.getRender());
      entityList.remove(despawnedEntity);
    }
  }

  private void addToEntityList() {
    entityList.addAll(entityBuffer);
    entityBuffer = new ArrayList<>();
  }

  private void handleGamePadPlayer() {
    if (gameParser.getPlayerList().size()
        > 1) { //FIXME: TESTCODE FOR CONTROLLER EVENTUALLY SUPPORT SIMUL CONTROLSCHEMES
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
        if (!entityRemove.contains(targetEntity)) {
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
        levelSelector.resetLevel(entityList, myViewManager);
        return;
      }
    }
  }

//<<<<<<< HEAD
//  private void resetLevel() {
//    nextLevel = 0;
//    myModelManager.resetPlayerValues(gameParser.getPlayerList());
//    despawnOldLevel();
//    levelSelector.updateCurrentLevel(entityList, myViewManager, nextLevel);
//    myModelManager.resetPlayerPositions(gameParser.getPlayerList());
//  }
//
//  //TODO: fix duplicated code if possible?
//  private void despawnOldLevel() {
//    List<EntityWrapper> entitiesToDespawn = new ArrayList<>();
//    for (EntityWrapper targetEntity : entityList) {
//      if (!gameParser.getPlayerList().contains(targetEntity)) {
//        entitiesToDespawn.add(targetEntity);
//      }
//    }
//    removeEntities(entitiesToDespawn);
//  }
//
//  private void handleSaveGame() {
//    if(myViewManager.getSaveGame()) {
//      JSONArray saveGame = new JSONArray();
//      JSONObject obj = new JSONObject();
//      for(int i = 0; i < levelSelector.getLevelsToPlay().size(); i++) {
//        obj.put("Level_" + (i+1), levelSelector.getLevelsToPlay().get(i).getLevelName());
//=======
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
//>>>>>>> c3c36ba4b00f396e0125d39bd184a271aa88ceb6
      }
    }
  }

