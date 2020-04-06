package ooga.model.actions;

import ooga.model.EntityModel;

public class AccelerateY extends Action {
  private double yAcceleration;

  public AccelerateY(String parameter){
    super(parameter);
    yAcceleration = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setYVelocity(entity.getYVelocity() + yAcceleration);
  }
}
