package ooga.model.actions;

import ooga.model.EntityModel;

public class ExecuteConditional extends Action {

  public ExecuteConditional(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    Action action = entity.getActionStack().pop();
    System.out.println(entity.getConditional());
    if(entity.getConditional()){
      action.execute(entity);
    }
  }
}
