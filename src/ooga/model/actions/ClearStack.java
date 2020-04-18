package ooga.model.actions;

import ooga.model.EntityModel;

public class ClearStack extends Action {

  public ClearStack(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.getActionStack().clear();
  }
}
