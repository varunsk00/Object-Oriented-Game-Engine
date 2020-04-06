package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveX extends Action {
  private double xdistance;

  public MoveX(String param){
    xdistance = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {

  }
}
