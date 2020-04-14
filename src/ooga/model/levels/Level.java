package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public class Level {

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;

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


  public void spawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    for (EntityWrapper player : playerEntities) {
      for (EntityWrapper tileEntity : tileEntities) {
        if (!playerEntities.contains(tileEntity) && isInRange(player.getModel(), tileEntity.getModel()) && !currentEntityList.contains(tileEntity)) {
          currentEntityList.add(tileEntity);
          viewManager.updateEntityGroup(tileEntity.getRender());
        }
      }
      for (EntityWrapper enemyEntity : enemyEntities) {
        if (!playerEntities.contains(enemyEntity) && isInRange(player.getModel(), enemyEntity.getModel()) && !currentEntityList.contains(enemyEntity)) {
          currentEntityList.add(enemyEntity);
          viewManager.updateEntityGroup(enemyEntity.getRender());
        }
      }
    }
  }

  private boolean isInRange(EntityModel subjectEntity, EntityModel targetEntity){
    if(Math.sqrt(Math.pow(subjectEntity.getX() - targetEntity.getX(), 2) + Math.pow(subjectEntity.getY() - targetEntity.getY(), 2)) < 2000){
      return true;
    }
    return false;
  }

}