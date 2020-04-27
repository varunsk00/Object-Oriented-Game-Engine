package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveY extends Action {

  private double yPosition;

  public MoveY(String parameter) {
    super(parameter);
    yPosition = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {entity.setY(entity.getY() + yPosition);}
}
