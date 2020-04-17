package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedRight extends Action {

  public SetBoundedRight(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setBoundedRight(Boolean.parseBoolean(param));
  }
}
