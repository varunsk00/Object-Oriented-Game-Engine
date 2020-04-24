package ooga.model.actions;

import ooga.model.EntityModel;

public class LoseHealth extends Action {

  public LoseHealth(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
//    entity.loseHealth(Double.parseDouble(param));
  }
}
