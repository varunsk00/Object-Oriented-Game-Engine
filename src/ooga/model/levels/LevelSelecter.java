package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public class LevelSelecter {

  private List<Level> parsedLevels;
  private List<Level> activeLevels;

  public LevelSelecter(List<Level> levelList){
    parsedLevels = levelList;
    activeLevels = new ArrayList<>();
  }

  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    for(Level level : activeLevels){
      level.despawnEntities(currentEntityList, viewManager);
      level.spawnEntities(currentEntityList, viewManager);
    }
  }

  public void infiniteLevelSwitch(){
    double randomSeed = Math.random();
    int randomIndex = (int) randomSeed * parsedLevels.size();
    activeLevels.add(parsedLevels.get(randomIndex));
  }

  public void specificLevelSwitch(int levelIndex){
    activeLevels = new ArrayList<>();
    activeLevels.add(parsedLevels.get(levelIndex));
  }

}
