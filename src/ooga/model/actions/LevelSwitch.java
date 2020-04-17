package ooga.model.actions;

import ooga.model.EntityModel;

public class LevelSwitch extends Action {
  private double nextLevelIndex;

  public LevelSwitch(String parameter){
    super(parameter);
    nextLevelIndex = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setLevelAdvancementStatus(true);
    entity.setNextLevelIndex((int) nextLevelIndex);
  }
}
