package ooga.model.actions;

import java.util.List;
import ooga.model.EntityModel;

public class ComplexAction extends Action {
  private List<Action> complexAction;

  public ComplexAction(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {

  }
}
