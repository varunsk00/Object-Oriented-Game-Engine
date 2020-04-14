package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public class LevelSelecter {

  private List<InfiniteLevel> parsedLevels;
  private List<InfiniteLevel> activeLevels;

  private static final int spawningInterval = 500;
  private int currentPlayerInterval = -1;


  public LevelSelecter(List<InfiniteLevel> levelList){
    parsedLevels = levelList;
    activeLevels = new ArrayList<>();
  }

  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    if(calculatePlayerInterval(currentEntityList.get(0)) > currentPlayerInterval) {
      currentPlayerInterval = calculatePlayerInterval(currentEntityList.get(0));
      infiniteLevelSwitch(currentEntityList, viewManager);
      for (InfiniteLevel level : activeLevels) {
        level.despawnEntities(currentEntityList, viewManager);
      }
    }
  }

  private void infiniteLevelSwitch(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    double randomSeed = Math.random();
    int randomIndex = (int) Math.floor(randomSeed * parsedLevels.size());
    InfiniteLevel level = parsedLevels.get(randomIndex);
    level.spawnEntities(currentEntityList, viewManager);
    activeLevels.add(level);

  }

  public void specificLevelSwitch(int levelIndex){
    activeLevels = new ArrayList<>();
    activeLevels.add(parsedLevels.get(levelIndex));
  }

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) player.getModel().getX()
        / spawningInterval; //TODO: generalize for X and Y scrollers

  }
}
