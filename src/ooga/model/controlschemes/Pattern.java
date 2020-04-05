package ooga.model.controlschemes;

import java.util.List;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class Pattern extends ControlScheme {
  private List<Action> actionList;
  private int index;

  public Pattern(){
  }

  public Pattern(List<Action> actions){
    actionList = actions;
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
