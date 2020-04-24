package ooga.model.levels;

import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.util.GameStatusProfile;


public class FiniteLevel extends Level{

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;


  public FiniteLevel(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList, GameStatusProfile gameProfile, String name) {
    super(tileList, playerList, enemyList, gameProfile, name);
    tileEntities = tileList;
    playerEntities = playerList;
    enemyEntities = enemyList;
  }



  @Override
  public void spawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager){
    for (EntityWrapper player : playerEntities) {
      for (EntityWrapper tileEntity : tileEntities) {
        if (!playerEntities.contains(tileEntity) && isInRange(player.getModel(), tileEntity.getModel()) && !currentEntityList.contains(tileEntity)) {
          currentEntityList.add(tileEntity);
          viewManager.addEntity(tileEntity.getRender());
        }
      }
      for (EntityWrapper enemyEntity : enemyEntities) {
        if (!playerEntities.contains(enemyEntity) && isInRange(player.getModel(), enemyEntity.getModel()) && !currentEntityList.contains(enemyEntity)) {
          currentEntityList.add(enemyEntity);
          viewManager.addEntity(enemyEntity.getRender());
        }
      }
    }
  }


}
