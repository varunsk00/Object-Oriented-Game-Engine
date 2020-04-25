package ooga.model.actions;

import java.util.List;
import ooga.model.EntityModel;
import ooga.util.ComplexActionParser;

public class ComplexAction extends Action {
  private List<Action> complexAction;

  public ComplexAction(String parameter) {
    super(parameter);
    String[] gameAndName = param.split("\\.");
    ComplexActionParser myParser = new ComplexActionParser(gameAndName[0], gameAndName[1]);
    complexAction = myParser.createComplexAction();
  }

  @Override
  public void execute(EntityModel entity) {
    entity.getActionStack().addAll(complexAction);
  }
}
