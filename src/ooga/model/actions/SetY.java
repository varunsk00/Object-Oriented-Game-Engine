package ooga.model.actions;

import ooga.model.EntityModel;

public class SetY extends Action {

  public SetY(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setY(Double.parseDouble(param));
  }
}
