package ooga.model.actions;

import ooga.model.EntityModel;

public class SetY extends Action {
  private double newY;

  public SetY(String parameter) {
    super(parameter);
    newY = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setY(newY);
  }
}
