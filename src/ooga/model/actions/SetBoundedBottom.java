package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedBottom extends Action {
  private boolean groundStatus;

  public SetBoundedBottom(String parameter){
    super(parameter);
    groundStatus = Boolean.valueOf(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setBoundedBelow(groundStatus);
    //System.out.println("help" + groundStatus);
  }
}
