package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedTop extends Action {

  public SetBoundedTop(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) { entity.setBoundedTop(Boolean.parseBoolean(param)); }
}
