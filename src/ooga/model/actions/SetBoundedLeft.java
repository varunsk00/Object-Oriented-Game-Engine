package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedLeft extends Action {

  public SetBoundedLeft(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setBoundedLeft(Boolean.parseBoolean(param));
  }
}
