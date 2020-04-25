package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.util.GameStatusProfile;
import ooga.view.application.Camera;

public class LevelSelector {

  private List<Level> parsedLevels;
  private Level activeLevel;
  private int spawningInterval;
  private GameStatusProfile gameStatusProfile;
  private Camera gameCamera;
  private List<EntityWrapper> playerList;
  private static final int cameraBuffer = 500;

  public LevelSelector(List<Level> levelList, List<EntityWrapper> players, GameStatusProfile gameProfile, Camera camera){
    parsedLevels = levelList;
    playerList = players;
    gameStatusProfile = gameProfile;
    gameCamera = camera;
    spawningInterval = gameStatusProfile.readSpawningInterval();
    int startingLevelIndex = gameStatusProfile.readStartingLevelIndex();
    activeLevel = parsedLevels.get(startingLevelIndex);

  }
// FIXME removed by ALex. Not deleting just in case but pretty sure we can delete this now

//  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager, int nextLevel) {
//    for(EntityWrapper player : playerList) {
//      if (player.getModel().getLevelAdvancementStatus()) {
//        player.getModel().setLevelAdvancementStatus(false);
//        switchLevel(nextLevel);
//        activeLevel.setCurrentPlayerInterval(calculatePlayerInterval(player));
//      }
//    }
//    activeLevel.spawnEntities(currentEntityList, viewManager);
//    this.despawnEntities(currentEntityList, viewManager);
//  }

  //TODO Refactored "changelevel" method added by alex. Called by Controller when the action "LevelSwitcH" is executed
  public void changeCurrentLevel(int nextLevel, EntityWrapper player) {
    switchLevel(nextLevel);
    activeLevel.setCurrentPlayerInterval(calculatePlayerInterval(player));
  }
  //TODO refactored by alex. Called in each update step of the main controller.
  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    activeLevel.spawnEntities(currentEntityList, viewManager);
    this.despawnEntities(currentEntityList, viewManager);
  }

  public void resetLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
    for(EntityWrapper player : playerList) {
      player.getModel().setHealth();
      player.getModel().setLevelAdvancementStatus(true);
      player.getModel().resetPosition();
    }

    despawnAllEntities(currentEntityList, viewManager);
    this.updateCurrentLevel(currentEntityList, viewManager);
    for(Level level : parsedLevels){
      level.setCurrentPlayerInterval(-1);
    }
  }


  private void switchLevel(int levelIndex){
    activeLevel = parsedLevels.get(levelIndex);
  }

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) Math.abs((player.getModel().getX() * gameStatusProfile.readScrollingStatusX() + player.getModel().getY() * gameStatusProfile.readScrollingStatusY())/spawningInterval);
  }

  private boolean isInRangeofCamera(EntityWrapper targetEntity){
    return targetEntity.getModel().getX() < gameCamera.getViewPort().getBoundsInParent().getMaxX() + cameraBuffer &&
        targetEntity.getModel().getX() > gameCamera.getViewPort().getBoundsInParent().getMinX() - cameraBuffer &&
        targetEntity.getModel().getY() < gameCamera.getViewPort().getBoundsInParent().getMaxY() + cameraBuffer &&
        targetEntity.getModel().getY() > gameCamera.getViewPort().getBoundsInParent().getMinY() - cameraBuffer;
  }

  public List<Level> getParsedLevels() {
    return parsedLevels.subList(parsedLevels.indexOf(activeLevel), parsedLevels.size());
  }


  private void despawnAllEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
    List<EntityWrapper> entitiesToDespawn = new ArrayList<>();
    for (EntityWrapper targetEntity : currentEntityList) {
      if (!playerList.contains(targetEntity)) {
        entitiesToDespawn.add(targetEntity);
      }
    }
    removeDespawnedEntities(currentEntityList, viewManager, entitiesToDespawn);
  }

  private void despawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    List<EntityWrapper> entitiesToDespawn = new ArrayList<>();
    for (EntityWrapper targetEntity : currentEntityList) {
      if (!playerList.contains(targetEntity) && !isInRangeofCamera(targetEntity)) {
        entitiesToDespawn.add(targetEntity);
      }
    }
    removeDespawnedEntities(currentEntityList, viewManager, entitiesToDespawn);
  }

  private void removeDespawnedEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager, List<EntityWrapper> entitiesToDespawn){
    for(EntityWrapper despawnedEntity : entitiesToDespawn){
      currentEntityList.remove(despawnedEntity);
      viewManager.removeEntity(despawnedEntity.getRender());
    }
  }

}
