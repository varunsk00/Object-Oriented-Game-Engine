package ooga.model.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

public class ActionFactory {

  private static final double X_MAX = 500; //TODO: set later
  private static final double Y_MAX = 250;
  private Map<String, String> myActions = new HashMap<>();
  private ResourceBundle actionBundle;

  public ActionFactory() {
    setGeneralCommands();

    /*
    setMovementCommands();
    setControlCommands();
     */
  }

  private void setGeneralCommands() {
    var actionBundle = ResourceBundle.getBundle(ActionFactory.class.getPackageName() + "resources." + "actions");
    for(String tag : actionBundle.keySet()) {
      myActions.put(tag, actionBundle.getString(tag));
    }
  }

  /*
  public Action makeAction(String action, Stack<Action> previous, Stack<List<Action>> listActions,
      Map<String, List<String>> myActions) {
    String formalAction = validateAction(action, myActions);

    return buildAction(formalAction);
  }

   */
}




