package ooga.model.actions;

import ooga.model.EntityModel;

public class SetOpacity extends Action {

  public SetOpacity(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setOpacity(Double.parseDouble(param));
  }
}
