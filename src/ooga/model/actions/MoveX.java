package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveX extends Action {
  private double xPosition;

  public MoveX(String parameter){
    super(parameter);
    xPosition = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setXVelocity(xPosition*(entity.getForwards() ? 1 : -1));
  }
}
