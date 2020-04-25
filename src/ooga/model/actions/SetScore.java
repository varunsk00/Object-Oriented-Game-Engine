package ooga.model.actions;

import ooga.model.EntityModel;

public class SetScore extends Action {
  private double score;

  public SetScore(String parameter) {
    super(parameter);
    score = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.updateScore(score);
  }
}
