package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveY extends Action {
  private double yVelocity;

  public MoveY(String parameter){
    super(parameter);
    yVelocity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setY(entity.getY() + yVelocity);
  }
}
