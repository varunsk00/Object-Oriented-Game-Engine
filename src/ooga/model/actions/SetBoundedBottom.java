package ooga.model.actions;

import ooga.model.EntityModel;

public class SetBoundedBottom extends Action {
  private boolean boundedBelow;

  public SetBoundedBottom(String parameter){
    super(parameter);
    boundedBelow = Boolean.valueOf(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setBoundedBelow(boundedBelow);
  }
}
