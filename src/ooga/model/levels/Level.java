package ooga.model.levels;

import com.thoughtworks.xstream.mapper.Mapper.Null;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.exceptions.ParameterInvalidException;
import ooga.model.EntityModel;
import ooga.util.GameStatusProfile;
import ooga.view.application.Camera;

public abstract class Level {
  private static final int SPAWN_RANGE = 1500;
  private static final int TWO = 2;
  private int currentPlayerInterval = -1;
  private String levelName;
  protected int scrollingStatusX;
  protected int scrollingStatusY;
  protected int spawningInterval;
  protected int levelSpawnOffset;

  public Level(List<EntityWrapper> tileList, List<EntityWrapper> playerList, List<EntityWrapper> enemyList, GameStatusProfile gameProfile, String name){
    levelName = name;
    scrollingStatusX = gameProfile.readScrollingStatusX();
    scrollingStatusY = gameProfile.readScrollingStatusY();
    spawningInterval = gameProfile.readSpawningInterval();
    levelSpawnOffset = gameProfile.readLevelSpawnOffset();
  }

  public abstract void spawnEntities(List<EntityWrapper> currentEntityList);

  public boolean isInRange(EntityModel subjectEntity, EntityModel targetEntity){
    return(Math.sqrt(Math.pow(subjectEntity.getX() - targetEntity.getX(), TWO) + Math.pow(subjectEntity.getY() - targetEntity.getY(), TWO)) < SPAWN_RANGE);
  }

  public void setCurrentPlayerInterval(int newInterval){
    currentPlayerInterval = newInterval;
  }

  public int getCurrentPlayerInterval(){
    return this.currentPlayerInterval;
  }

  public String getLevelName() {
    return levelName;
  }

  public void addEntity(EntityWrapper entity, List<EntityWrapper> currentEntityList){
    currentEntityList.add(entity);
  }

  public int calculatePlayerInterval(EntityWrapper player) {
    return (int) Math.abs((player.getModel().getX() * this.scrollingStatusX + player.getModel().getY() * this.scrollingStatusY)/spawningInterval);
  }

}
