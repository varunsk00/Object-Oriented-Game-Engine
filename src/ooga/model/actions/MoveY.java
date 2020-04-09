package ooga.model.actions;

import ooga.model.EntityModel;

public class MoveY extends Action {
  private double ydistance;

  public MoveY(String parameter){
    super(parameter);
    ydistance = Double.parseDouble(param);
    System.out.println("movey");
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setY(entity.getY() + ydistance);
  }
}
