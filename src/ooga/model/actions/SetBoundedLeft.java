package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedLeft extends Action {
  private boolean boundedLeft;

  public SetBoundedLeft(String parameter) {
    super(parameter);
    boundedLeft = Boolean.parseBoolean(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setBoundedLeft(boundedLeft);
  }
}
