package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public class InfiniteLevel {

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;
  private static final int spawningInterval = 1000;
  private int currentPlayerInterval;

  public InfiniteLevel(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList){
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


  public void spawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
    for (EntityWrapper player : playerEntities) {
      System.out.println(calculatePlayerInterval(player));
      System.out.println(currentPlayerInterval);
      if (calculatePlayerInterval(player) > currentPlayerInterval) {
        currentPlayerInterval = calculatePlayerInterval(player);
        int tileInterval = currentPlayerInterval + 1;

        for (EntityWrapper tileEntity : tileEntities) {
          if (!playerEntities.contains(tileEntity) && isInRange(player.getModel(),
              tileEntity.getModel()) && !currentEntityList.contains(tileEntity)) {
            setEntityPositions(tileEntity, tileInterval);
            currentEntityList.add(tileEntity);
            viewManager.updateEntityGroup(tileEntity.getRender());
          }
        }
        for (EntityWrapper enemyEntity : enemyEntities) {
          if (!playerEntities.contains(enemyEntity) && isInRange(player.getModel(),
              enemyEntity.getModel()) && !currentEntityList.contains(enemyEntity)) {
            setEntityPositions(enemyEntity, tileInterval);
            currentEntityList.add(enemyEntity);
            viewManager.updateEntityGroup(enemyEntity.getRender());
          }
        }
      }
    }
  }

  private void setEntityPositions(EntityWrapper targetEntity, int tileInterval){
    targetEntity.getModel().setX(tileInterval * spawningInterval); //TODO: generalize for X and Y scrollers
  }

  private boolean isInRange(EntityModel subjectEntity, EntityModel targetEntity){
    if(Math.sqrt(Math.pow(subjectEntity.getX() - targetEntity.getX(), 2) + Math.pow(subjectEntity.getY() - targetEntity.getY(), 2)) < 2000){
      return true;
    }
    return false;
  }

  private int calculatePlayerInterval(EntityWrapper player){
    return (int) player.getModel().getX() / spawningInterval; //TODO: generalize for X and Y scrollers

  }

}
