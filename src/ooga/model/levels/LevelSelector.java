package ooga.model.levels;

import java.util.ArrayList;
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

  public void updateCurrentLevel(List<EntityWrapper> currentEntityList, ViewManager viewManager){
//    System.out.print(currentPlayerInterval);
//    System.out.println("     " + calculatePlayerInterval(currentEntityList.get(0)));
//    System.out.println(currentEntityList.get(0).getModel().getLevelAdvancementStatus());
    if(currentEntityList.get(0).getModel().getLevelAdvancementStatus() && calculatePlayerInterval(currentEntityList.get(0)) > currentPlayerInterval){
      //TODO: find a better way that the interval to spawn pipes only once
      currentPlayerInterval = calculatePlayerInterval(currentEntityList.get(0));
      switchLevel(currentEntityList.get(0).getModel().getNextLevelIndex());
      currentEntityList.get(0).getModel().setLevelAdvancementStatus(false);
    }
    activeLevel.spawnEntities(currentEntityList, viewManager);
    activeLevel.despawnEntities(currentEntityList, viewManager);
  }

  private void switchLevel(int levelIndex){
    activeLevel = null;
    activeLevel = parsedLevels.get(levelIndex);
  }

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) player.getModel().getX()
        / spawningInterval; //TODO: generalize for X and Y scrollers
  }
}
