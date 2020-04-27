package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedRight extends Action {
  private boolean boundedRight;

  public SetBoundedRight(String parameter) {
    super(parameter);
    boundedRight = Boolean.parseBoolean(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setBoundedRight(boundedRight);
  }
}
