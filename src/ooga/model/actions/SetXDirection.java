package ooga.model.actions;

import ooga.model.EntityModel;

public class SetXDirection extends Action {

  public SetXDirection(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setForwards(param.equals("forwards"));
  }
}
