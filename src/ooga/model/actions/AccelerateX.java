package ooga.model.actions;

import ooga.model.EntityModel;

public class AccelerateX extends Action {
  private double xAcceleration;

  public AccelerateX(String parameter){
    super(parameter);
    xAcceleration = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setXVelocity(entity.getXVelocity() + xAcceleration);
  }
}
