package ooga.model.actions;

import ooga.model.EntityModel;

public class RandomLevelSwitch extends Action {
  private int nextLevelIndex;

  public RandomLevelSwitch(String parameter){
    super(parameter);
    nextLevelIndex = Integer.parseInt(param);
  }

  @Override
  public void execute(EntityModel entity) {
    double randomProportion = Math.random();
    nextLevelIndex = (int) Math.floor(randomProportion * Integer.parseInt(param));
    entity.changeLevel((int) Math.floor(randomProportion * nextLevelIndex));
  }
}
