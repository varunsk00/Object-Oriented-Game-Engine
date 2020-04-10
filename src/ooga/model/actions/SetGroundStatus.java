package ooga.model.actions;

import ooga.model.EntityModel;

public class SetGroundStatus extends Action {
  private boolean groundStatus;

  public SetGroundStatus(String parameter){
    super(parameter);
    groundStatus = Boolean.valueOf(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setOnGround(groundStatus);
    System.out.println("help" + groundStatus);
  }
}
