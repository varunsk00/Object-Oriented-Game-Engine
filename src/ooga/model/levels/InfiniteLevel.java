package ooga.model.levels;

import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.util.GameStatusProfile;

public class InfiniteLevel extends Level{

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;

  public InfiniteLevel(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList, GameStatusProfile gameProfile, String name) {
    super(tileList, playerList, enemyList, gameProfile, name);
    tileEntities = tileList;
    playerEntities = playerList;
    enemyEntities = enemyList;
  }

  @Override
  public void spawnEntities(List<EntityWrapper> currentEntityList) {
    for (EntityWrapper player : playerEntities) {
      if (calculatePlayerInterval(player) > this.getCurrentPlayerInterval()) {
        this.setCurrentPlayerInterval(calculatePlayerInterval(player));
        int tileInterval = calculatePlayerInterval(player) + levelSpawnOffset;

        for (EntityWrapper tileEntity : tileEntities) {
            EntityWrapper newSpawn = new EntityWrapper(tileEntity.getEntityID(), tileEntity.getController());
            resizeEntity(newSpawn, tileEntity);
            setEntityPositions(newSpawn, tileEntity, tileInterval);
            addEntity(newSpawn, currentEntityList);
          }
        for (EntityWrapper enemyEntity : enemyEntities) {
            EntityWrapper newSpawn = new EntityWrapper(enemyEntity.getEntityID(), enemyEntity.getController());
            setEntityPositions(newSpawn, enemyEntity, tileInterval);
            addEntity(newSpawn, currentEntityList);
        }
      }
    }
  }

  private void resizeEntity(EntityWrapper newSpawn, EntityWrapper copyEntity) {
    newSpawn.getModel()
        .setHeight(copyEntity.getModel().getHeight());
    newSpawn.getModel()
        .setWidth(copyEntity.getModel().getWidth());
  }

  private void setEntityPositions(EntityWrapper newSpawn, EntityWrapper copyEntity, int tileInterval) {
    double spawnCoordinate = tileInterval * this.spawningInterval;
    newSpawn.getModel().setX(spawnCoordinate * this.scrollingStatusX + copyEntity.getModel().getX() * this.scrollingStatusY + copyEntity.getModel().getX() * this.scrollingStatusX);

    newSpawn.getModel().setY(-spawnCoordinate * this.scrollingStatusY + copyEntity.getModel().getY() * this.scrollingStatusX + copyEntity.getModel().getY() * this.scrollingStatusY);
  }


}
