package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveX extends Action {
  private double xVelocity;

  public MoveX(String parameter){
    super(parameter);
    xVelocity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setXVelocity(xVelocity*(entity.getForwards() ? 1 : -1));
  }
}
