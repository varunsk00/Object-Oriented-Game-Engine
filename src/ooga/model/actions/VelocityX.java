package ooga.model.actions;

import ooga.model.EntityModel;

public class VelocityX extends Action {
  private double xVelocity;

  public VelocityX(String parameter){
    super(parameter);
    xVelocity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setXVelocity(xVelocity*(entity.getForwards() ? 1 : -1));
  }
}
