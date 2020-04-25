package ooga.model.actions;

import ooga.model.EntityModel;

public class IncrementScore extends Action {

  public IncrementScore(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.updateScore(entity.getScore() + Double.parseDouble(param));
  }
}
