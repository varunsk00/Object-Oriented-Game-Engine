package ooga.model.levels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.model.EntityModel;

public class InfiniteLevel extends Level{

  private List<EntityWrapper> tileEntities;
  private List<EntityWrapper> playerEntities;
  private List<EntityWrapper> enemyEntities;
  private static final int spawningInterval = 500;

  public InfiniteLevel(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList, String name) {
    super(tileList, playerList, enemyList, name);
    tileEntities = tileList;
    playerEntities = playerList;
    enemyEntities = enemyList;
  }


  @Override
  public void spawnEntities(List<EntityWrapper> currentEntityList, ViewManager viewManager) {
//    System.out.println(getCurrentPlayerInterval() + "     " + calculatePlayerInterval(currentEntityList.get(0)));
    for (EntityWrapper player : playerEntities) {
      if (calculatePlayerInterval(player) > this.getCurrentPlayerInterval()) {
        this.setCurrentPlayerInterval(calculatePlayerInterval(currentEntityList.get(0)));
        int tileInterval = calculatePlayerInterval(player) + 2; //TODO: find a better way to spawn pipes outwards

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

  private int calculatePlayerInterval(EntityWrapper player) {
    return (int) player.getModel().getX()
        / spawningInterval; //TODO: generalize for X and Y scrollers

  }

}
