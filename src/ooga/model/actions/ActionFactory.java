package ooga.model.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ActionFactory {

  private static final double X_MAX = 500; //TODO: set later
  private static final double Y_MAX = 250;

  public ActionFactory() {
    /*setGeneralCommands();
    setMovementCommands();
    setControlCommands();

     */
  }

  public Action makeAction(String action, Stack<Action> previous, Stack<List<Action>> listActions,
      Map<String, List<String>> myCommands) {// throws InvalidArgumentException{
    //String formalCommand = validateAction(command, myCommands);

    List<Action> commands = new ArrayList<>();
//    int count = getCount(formalCommand);

    if (previous.size() + listActions.size() < 0) { //TODO: fix count var){
      //throw new InvalidArgumentException(String.format("Incorrect number of arguments for command %s", command));
    }
    return null;

  }
}




