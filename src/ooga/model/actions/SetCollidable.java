package ooga.model.actions;

import ooga.model.EntityModel;
//maybe unnecessary
public class SetCollidable extends Action {

  public SetCollidable(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setCollidable(Boolean.parseBoolean(param));
  }
}
