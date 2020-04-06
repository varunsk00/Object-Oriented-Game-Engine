package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveY extends Action {
  private double ydistance;

  public MoveY(String param){
    ydistance = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {

  }
}
