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
  private static final int spawningInterval = 500;
  private int currentPlayerInterval = -1;

  public InfiniteLevel(List<EntityWrapper> tileList, List<EntityWrapper> playerList,
      List<EntityWrapper> enemyList) {
    tileEntities = tileList;
    playerEntities = playerList;
    enemyEntities = enemyList;
  }

  public void despawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
    for (EntityWrapper player : playerEntities) {
      List<EntityWrapper> entitiesToDespawn = new ArrayList<>();
      for (EntityWrapper targetEntity : currentEntityList) {
        if (!playerEntities.contains(targetEntity) && !isInRange(player.getModel(),
            targetEntity.getModel())) {
          entitiesToDespawn.add(targetEntity);
        }
      }
      for (EntityWrapper despawnedEntity : entitiesToDespawn) {
        currentEntityList.remove(despawnedEntity);
        viewManager.removeEntityGroup(despawnedEntity.getRender());
      }
    }
  }


  public void spawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
    for (EntityWrapper player : playerEntities) {
      if (calculatePlayerInterval(player) > currentPlayerInterval) {
        currentPlayerInterval = calculatePlayerInterval(player);
        int tileInterval = currentPlayerInterval + 2; //TODO: find a better way to spawn pipes outwards

        for (EntityWrapper tileEntity : tileEntities) {
            EntityWrapper newSpawn = new EntityWrapper(tileEntity.getEntityID(), tileEntity.getController()); //FIXME: Need a better way to make the new entity
            setEntityPositions(newSpawn, tileInterval, tileEntity.getModel().getY());
            currentEntityList.add(newSpawn);
            viewManager.updateEntityGroup(newSpawn.getRender());
          }

        for (EntityWrapper enemyEntity : enemyEntities) {
          if (!playerEntities.contains(enemyEntity) && isInRange(player.getModel(), enemyEntity.getModel())) {
            EntityWrapper newSpawn = new EntityWrapper(enemyEntity.getEntityID(), enemyEntity.getController());
            setEntityPositions(newSpawn, tileInterval, enemyEntity.getModel().getY());
            currentEntityList.add(newSpawn);
            viewManager.updateEntityGroup(newSpawn.getRender());
          }
        }
      }
    }
  }

  private void setEntityPositions(EntityWrapper targetEntity, int tileInterval, double yPosition) {
    targetEntity.getModel()
        .setX(tileInterval * spawningInterval); //TODO: generalize for X and Y scrollers
    targetEntity.getModel()
        .setY(yPosition); //TODO: generalize for X and Y scrollers
  }

  private boolean isInRange(EntityModel subjectEntity, EntityModel targetEntity) {
    if (Math.sqrt(Math.pow(subjectEntity.getX() - targetEntity.getX(), 2) + Math
        .pow(subjectEntity.getY() - targetEntity.getY(), 2)) < 2000) {
      return true;
    }
    return false;
  }

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) player.getModel().getX()
        / spawningInterval; //TODO: generalize for X and Y scrollers

  }

}
