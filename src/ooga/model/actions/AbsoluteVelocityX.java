package ooga.model.actions;

import ooga.model.EntityModel;

public class AbsoluteVelocityX extends Action {
  private double xVelocity;

  public AbsoluteVelocityX(String parameter){
    super(parameter);
    xVelocity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setXVelocity(xVelocity*(entity.getForwards() ? 1 : -1));
  }
}
