package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.model.actions.NoAction;

public class Keybindings extends ControlScheme {

  public Keybindings(Map<String, Action> bindings){
    super(bindings);
    currentAction = new ArrayList<>();
  }

  @Override
  public List<Action> getCurrentAction() {
    return currentAction;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {
    if(actionMap.containsKey(keyEvent.getCode().toString()) && !currentAction.contains(actionMap.get(keyEvent.getCode().toString()))) {
      currentAction.add(actionMap.get(keyEvent.getCode().toString()));
    }
  }

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {
    if(actionMap.containsKey(keyEvent.getCode().toString())) {
      currentAction.remove(actionMap.get(keyEvent.getCode().toString()));
    }
  }
}
