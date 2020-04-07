package ooga.model.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import ooga.model.EntityModel;
import ooga.model.actions.actionExceptions.InvalidActionException;

public class ActionFactory {

  private static final double X_MAX = 500; //TODO: set later
  private static final double Y_MAX = 250;
  private Map<String, String> myActions = new HashMap<>();
  private ResourceBundle actionBundle;
  private EntityModel myEntityModel;
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";

  public ActionFactory() {
    setGeneralCommands();

    /*
    setMovementCommands();
    setControlCommands();
     */
  }

  public void setMyEntityModel(EntityModel entityModel) {
    this.myEntityModel = entityModel;
  }

  private void setGeneralCommands() {
    var actionBundle = ResourceBundle.getBundle(ActionFactory.class.getPackageName() + ".resources." + "actions");
    for(String tag : actionBundle.keySet()) {
      myActions.put(tag, actionBundle.getString(tag));
    }
  }


  public Action makeAction(String action, String param) throws InvalidActionException {
    //String formalAction = action;
    String formalAction = validateAction(action); //TODO: check if action is valid

    return buildAction(formalAction, param);
  }

  private String validateAction(String action)
      throws InvalidActionException {
    if(myActions.containsKey(action)) {
      return action;
    }
    throw new InvalidActionException("Action does not exist"); //TODO: fix exception handling
  }

  private Action buildAction(String formalAction, String param) {
    try {
      return (Action) Class.forName(ACTIONS_PREFIX + formalAction).getDeclaredConstructor(String.class).newInstance(param);
    }
    catch
      (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new InvalidActionException("Action could not be found.");
    }
  }


}




