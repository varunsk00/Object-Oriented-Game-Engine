package ooga.model.actions;

import ooga.model.EntityModel;

public class SetPermeable extends Action {
  private boolean permeable;

  public SetPermeable(String parameter) {
    super(parameter);
    permeable = Boolean.parseBoolean(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setPermeable(permeable);
  }
}
