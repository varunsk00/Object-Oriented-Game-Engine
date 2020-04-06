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
  public Action getCurrentAction() {
    Action output = actionList.get(index);
    index++;
    if(index >= actionList.size()){
      index = 0;
    }
    return output;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}
}
