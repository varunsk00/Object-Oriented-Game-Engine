package ooga.model.actions;

import ooga.model.EntityModel;

public class SetX extends Action{
  private double newX;

  public SetX(String parameter) {
    super(parameter);
    newX = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {entity.setX(newX);}
}
