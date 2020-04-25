package ooga.model.levels;

import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.util.GameStatusProfile;

public class InfiniteLevel extends Level{

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;
  private int scrollingStatusX;
  private int scrollingStatusY;
  private int spawningInterval;
  private int levelSpawnOffset;
  private GameStatusProfile gameStatusProfile;

  public InfiniteLevel(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList, GameStatusProfile gameProfile, String name) {
    super(name);
    tileEntities = tileList;
    playerEntities = playerList;
    enemyEntities = enemyList;
    gameStatusProfile = gameProfile;
    scrollingStatusX = gameStatusProfile.readScrollingStatusX();
    scrollingStatusY = gameStatusProfile.readScrollingStatusY();
    spawningInterval = gameStatusProfile.readSpawningInterval();
    levelSpawnOffset = gameStatusProfile.readLevelSpawnOffset();
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
            addEntityToListAndViewManager(newSpawn, currentEntityList);
          }
        for (EntityWrapper enemyEntity : enemyEntities) {
            EntityWrapper newSpawn = new EntityWrapper(enemyEntity.getEntityID(), enemyEntity.getController());
            setEntityPositions(newSpawn, enemyEntity, tileInterval);
            addEntityToListAndViewManager(newSpawn, currentEntityList);
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
    newSpawn.getModel()
        .setX(spawnCoordinate * this.scrollingStatusX + copyEntity.getModel().getX() * this.scrollingStatusY);
    newSpawn.getModel()
        .setY(-spawnCoordinate * this.scrollingStatusY + copyEntity.getModel().getY() * this.scrollingStatusX);
  }

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) Math.abs((player.getModel().getX() * this.scrollingStatusX + player.getModel().getY() * this.scrollingStatusY)/spawningInterval);
  }

}
