package ooga.model.actions;

import ooga.model.EntityModel;

public class SetScore extends Action {

  public SetScore(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.updateScore(Double.parseDouble(param));
  }
}
