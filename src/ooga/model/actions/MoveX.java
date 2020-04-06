package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveX extends Action {
  private double xdistance;

  public MoveX(String parameter){
    super(parameter);
    xdistance = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setX(entity.getX() + xdistance);
  }
}
