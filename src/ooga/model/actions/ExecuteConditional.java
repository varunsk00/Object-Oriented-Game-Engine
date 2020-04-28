package ooga.model.actions;

import ooga.model.EntityModel;

public class ExecuteConditional extends Action {

  public ExecuteConditional(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    if(entity.getConditional()){
      entity.getActionStack().pop().execute(entity);
    }
    else{
      entity.getActionStack().pop();
    }
  }
}
