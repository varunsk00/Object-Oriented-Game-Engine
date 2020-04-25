package ooga.model.actions;

import ooga.model.EntityModel;

public class RandomLevelSwitch extends Action {
  private double nextLevelIndex;

  public RandomLevelSwitch(String parameter){
    super(parameter);
    nextLevelIndex = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    double randomProportion = Math.random();
    nextLevelIndex = (int) Math.floor(randomProportion * Double.parseDouble(param));
    entity.changeLevel((int) Math.floor(randomProportion * nextLevelIndex));
  }
}
