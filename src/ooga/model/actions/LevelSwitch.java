package ooga.model.actions;

import ooga.model.EntityModel;

public class LevelSwitch extends Action {

  private int nextLevelIndex;

  public LevelSwitch(String parameter) {
    super(parameter);
    nextLevelIndex = Integer.parseInt(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.changeLevel(nextLevelIndex);
  }
}
