package ooga.model.actions;

import ooga.model.EntityModel;

public class SetX extends Action{

  public SetX(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setX(Double.parseDouble(param));
  }
}
