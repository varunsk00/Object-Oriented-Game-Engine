package ooga.model.levels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public class LevelSelector {

  private List<Level> parsedLevels;
  private Level activeLevel;


  private static final int spawningInterval = 500;
  private int currentPlayerInterval = -1;


  public LevelSelector(List<Level> levelList){
    parsedLevels = levelList;
    activeLevel = parsedLevels.get(0);
  }

  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
//    System.out.println(currentEntityList.size());
    if (currentEntityList.get(0).getModel().getLevelAdvancementStatus()) {
      //TODO: find a better way that the interval to spawn pipes only once
      currentEntityList.get(0).getModel().setLevelAdvancementStatus(false);
      switchLevel(currentEntityList.get(0).getModel().getNextLevelIndex());
      activeLevel.setCurrentPlayerInterval(calculatePlayerInterval(currentEntityList.get(0)));
    }
    activeLevel.spawnEntities(currentEntityList, viewManager);
    activeLevel.despawnEntities(currentEntityList, viewManager);
//    this.despawnEntities(currentEntityList, viewManager);

  }

  public void restartLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
//    System.out.println(currentEntityList.size());
    if (currentEntityList.get(0).getModel().getLevelAdvancementStatus()) {
      //TODO: find a better way that the interval to spawn pipes only once
      currentEntityList.get(0).getModel().setLevelAdvancementStatus(false);
      switchLevel(currentEntityList.get(0).getModel().getNextLevelIndex() - 1);
      activeLevel.setCurrentPlayerInterval(calculatePlayerInterval(currentEntityList.get(0)));
    }
    activeLevel.spawnEntities(currentEntityList, viewManager);
    activeLevel.despawnEntities(currentEntityList, viewManager);
//    this.despawnEntities(currentEntityList, viewManager);

  }

  private void switchLevel(int levelIndex){
    activeLevel = parsedLevels.get(levelIndex);
  }

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) player.getModel().getX()
        / spawningInterval; //TODO: generalize for X and Y scrollers
  }
//TODO: decide whether or not to have despawning done by level selector or levels (actually has
// nothing pertaining to individual levels.
  private void despawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager){
      List<EntityWrapper> entitiesToDespawn = new ArrayList<>();
      for (EntityWrapper targetEntity : currentEntityList) {
        if (!currentEntityList.get(0).equals(targetEntity) && !isInRange(currentEntityList.get(0).getModel(), targetEntity.getModel())) {
          entitiesToDespawn.add(targetEntity);
        }
      }
      for(EntityWrapper despawnedEntity : entitiesToDespawn){
        currentEntityList.remove(despawnedEntity);
        viewManager.removeEntityGroup(despawnedEntity.getRender());
      }
    }

  private boolean isInRange(EntityModel subjectEntity, EntityModel targetEntity){
    if(Math.sqrt(Math.pow(subjectEntity.getX() - targetEntity.getX(), 2) + Math.pow(subjectEntity.getY() - targetEntity.getY(), 2)) < 1500){
      return true;
    }
    return false;
  }

  public List<Level> getLevelsToPlay() {
    return parsedLevels.subList(parsedLevels.indexOf(activeLevel), parsedLevels.size());
  }
}
