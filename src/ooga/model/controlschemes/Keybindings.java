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
    if(actionMap.containsKey(keyEvent.getText())) {
      currentAction.add(actionMap.get(keyEvent.getText()));
    }
  }

  @Override
  public void handleKeyReleased() {
    currentAction = new ArrayList<>();
  }
}
