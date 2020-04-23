package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public abstract class Level {

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;
  private static final int TWO = 2;
  private int currentPlayerInterval = -1;

  public Level(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList){
    tileEntities = tileList;
    playerEntities = playerList;
    enemyEntities = enemyList;
  }

  public void despawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    for (EntityWrapper player : playerEntities) {
      List<EntityWrapper> entitiesToDespawn = new ArrayList<>();
      for (EntityWrapper targetEntity : currentEntityList) {
        if (!playerEntities.contains(targetEntity) && !isInRange(player.getModel(), targetEntity.getModel())) {
          entitiesToDespawn.add(targetEntity);
        }
      }
      for(EntityWrapper despawnedEntity : entitiesToDespawn){
        currentEntityList.remove(despawnedEntity);
        viewManager.removeEntityGroup(despawnedEntity.getRender());
      }
    }
  }


  public abstract void spawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager);

  public boolean isInRange(EntityModel subjectEntity, EntityModel targetEntity){
    if(Math.sqrt(Math.pow(subjectEntity.getX() - targetEntity.getX(), TWO) + Math.pow(subjectEntity.getY() - targetEntity.getY(), TWO)) < 1500){
      return true;
    }
    return false;
  }

  public void setCurrentPlayerInterval(int newInterval){
    currentPlayerInterval = newInterval;
  }

  public int getCurrentPlayerInterval(){
    return this.currentPlayerInterval;
  }


}
