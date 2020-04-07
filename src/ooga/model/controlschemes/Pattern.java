package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class Pattern extends ControlScheme {
  private List<Action> actionList;
  private int index;

  public Pattern(Map<String, Action> actions){
    super(actions);
    actionList = new ArrayList<>();
    for(String s : actionMap.keySet()){
      actionList.add(Integer.parseInt(s), actionMap.get(s));
    }
    index = 0;
  }
  @Override
  public List<Action> getCurrentAction() {
    Action output = actionList.get(index);
    index++;
    if(index >= actionList.size()){
      index = 0;
    }
    List<Action> currentActions = new ArrayList<>();
    currentActions.add(output);
    return currentActions;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {return;}
}
