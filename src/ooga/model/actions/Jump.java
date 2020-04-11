package ooga.model.actions;

import ooga.model.EntityModel;

public class Jump extends Action {
  private double yVelocity;

  public Jump(String parameter){
    super(parameter);
    yVelocity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    if(entity.isOnGround()) {
      soundBoard.playSoundEffect(entity.getEntityID() + "_Jump");
      entity.setYVelocity(yVelocity);
      entity.setOnGround(false);
    }
  }
}
