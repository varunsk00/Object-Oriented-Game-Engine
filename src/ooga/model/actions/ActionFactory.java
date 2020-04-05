package ooga.model.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ActionFactory {
  private static final double X_MAX = 500; //TODO: set later
  private static final double Y_MAX = 250;

  public ActionFactory() {
    setGeneralCommands();
    setMovementCommands();
    setControlCommands();
  }

  public Action makeCommand(String command, Stack<Command> previous, Stack<List<Command>> listCommands, Map<String, List<String>> myCommands) throws InvalidArgumentException{
    String formalCommand = validateCommand(command, myCommands);

    List<Command> commands = new ArrayList<>();
    int count = getCount(formalCommand);

    if(previous.size() + listCommands.size() < count){ //TODO: TYLER EDITED
      throw new InvalidArgumentException(String.format("Incorrect number of arguments for command %s", command));
    }
    if (formalCommand.equals("Tell")) {
      while (commands.size() + listCommands.size() < count) {
        if (previous.size() > 0) {
          commands.add(previous.pop());
        } else {
          break;
        }
      }
    } else {
      while (commands.size() < count) {
        if (previous.size() > 0) {
          commands.add(previous.pop());
        } else {
          break;
        }
      }
    }
    return buildCommand(formalCommand, commands, listCommands);
  }



}
