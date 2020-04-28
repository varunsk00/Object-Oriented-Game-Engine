package ooga.model.actions;

import ooga.model.EntityModel;

public class IncrementScore extends Action {
  private double newScore;

  public IncrementScore(String parameter) {
    super(parameter);
    newScore = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.updateScore(entity.getScore() + newScore);
  }
}
