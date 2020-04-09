package ooga.model.actions;

import ooga.model.EntityModel;

public class VelocityY extends Action {
  private double yVelocity;

  public VelocityY(String parameter){
    super(parameter);
    yVelocity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setXVelocity(yVelocity);
  }
}
