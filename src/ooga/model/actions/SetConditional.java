package ooga.model.actions;

import ooga.model.EntityModel;

public class SetConditional extends Action {
  boolean conditionalValue;

  public SetConditional(String parameter) {
    super(parameter);
    conditionalValue = Boolean.parseBoolean(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setConditional(Boolean.parseBoolean(param));
  }
}
