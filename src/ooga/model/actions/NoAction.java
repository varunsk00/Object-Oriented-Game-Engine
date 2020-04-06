package ooga.model.actions;

import ooga.model.EntityModel;

public class NoAction extends Action {

  public NoAction(){
    super("");
  }

  public NoAction(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    return;
  }
}
