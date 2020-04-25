package ooga.model.actions;

import ooga.model.EntityModel;

public class SetPermeable extends Action {

  public SetPermeable(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setPermeable(Boolean.parseBoolean(param));
  }
}
