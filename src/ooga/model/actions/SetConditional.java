package ooga.model.actions;

import ooga.model.EntityModel;

public class SetConditional extends Action {

  public SetConditional(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setConditional(Boolean.parseBoolean(param));
  }
}
