package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.util.GameStatusProfile;
import ooga.view.application.Camera;

public class LevelSelector {

  private List<Level> parsedLevels;
  private Level activeLevel;
  private GameStatusProfile gameStatusProfile;
  private Camera gameCamera;
  private List<EntityWrapper> playerList;
  private static final int ORIGINAL_LEVEL_INTERVAL = -1;
  private static final int CAMERA_BUFFER = 500;

  public LevelSelector(List<Level> levelList, List<EntityWrapper> players, GameStatusProfile gameProfile, Camera camera){
    parsedLevels = levelList;
    playerList = players;
    gameStatusProfile = gameProfile;
    gameCamera = camera;
    int startingLevelIndex = gameStatusProfile.readStartingLevelIndex();
    activeLevel = parsedLevels.get(startingLevelIndex);
  }

  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, List<EntityWrapper> entitiesToDespawn, List<EntityWrapper> entityRemoved){
    activeLevel.spawnEntities(currentEntityList, entityRemoved);
    this.despawnEntities(currentEntityList, entitiesToDespawn);
  }

  public void changeCurrentLevel(int nextLevel, EntityWrapper player) {
    switchLevel(nextLevel);
    for (EntityWrapper tempPlayer : playerList) {
      if(!tempPlayer.equals(player)) {
        tempPlayer.getModel().loadStats();
      }
    }
    activeLevel.setCurrentPlayerInterval(activeLevel.calculatePlayerInterval(player));
  }

  public void resetLevel(List<EntityWrapper> currentEntityList, List<EntityWrapper> entitiesToDespawn) {
    for(EntityWrapper player : playerList) {
      player.getModel().setHealth();
      player.getModel().loadStats();
    }
    despawnAllEntities(currentEntityList, entitiesToDespawn);
    this.updateCurrentLevel(currentEntityList, entitiesToDespawn, new ArrayList<EntityWrapper>());
    for(Level level : parsedLevels){
      level.setCurrentPlayerInterval(ORIGINAL_LEVEL_INTERVAL);
    }
  }


  private void switchLevel(int levelIndex){
    activeLevel = parsedLevels.get(levelIndex);
  }

  private void despawnAllEntities(List<EntityWrapper> currentEntityList, List<EntityWrapper> entitiesToDespawn) {
    System.out.println("all entity gone");
    for (EntityWrapper targetEntity : currentEntityList) {
      if (!playerList.contains(targetEntity)) {
        entitiesToDespawn.add(targetEntity);
      }
    }

  }

  private void despawnEntities(List<EntityWrapper> currentEntityList, List<EntityWrapper> entitiesToDespawn){
    for (EntityWrapper targetEntity : currentEntityList) {
      if (!playerList.contains(targetEntity) && !isInRangeofCamera(targetEntity)) {
        entitiesToDespawn.add(targetEntity);
      }
    }
  }

  public List<Level> getParsedLevels() {
    return parsedLevels.subList(parsedLevels.indexOf(activeLevel), parsedLevels.size());
  }

  private boolean isInRangeofCamera(EntityWrapper targetEntity){
    return targetEntity.getModel().getX() < gameCamera.getViewPort().getBoundsInParent().getMaxX() + CAMERA_BUFFER &&
        targetEntity.getModel().getX() > gameCamera.getViewPort().getBoundsInParent().getMinX() - CAMERA_BUFFER &&
        targetEntity.getModel().getY() < gameCamera.getViewPort().getBoundsInParent().getMaxY() + CAMERA_BUFFER &&
        targetEntity.getModel().getY() > gameCamera.getViewPort().getBoundsInParent().getMinY() - CAMERA_BUFFER;
  }

}
