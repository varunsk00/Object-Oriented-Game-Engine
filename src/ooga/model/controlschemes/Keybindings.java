package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class Keybindings extends ControlScheme {
  private Map<String, Action> keyBindings;

  public Keybindings(List<Map<String, Action>> bindings){
    super(bindings);
    keyBindings = new HashMap<>();
    for(Map<String, Action> map : actionMap){
      keyBindings.putAll(map);
    }
    currentAction = new ArrayList<>();
  }

  @Override
  public List<Action> getCurrentAction() {
    return currentAction;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {
    if(keyBindings.containsKey(keyEvent.getCode().toString()) && !currentAction.contains(keyBindings.get(keyEvent.getCode().toString()))) {
      currentAction.add(keyBindings.get(keyEvent.getCode().toString()));
    }
  }

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {
    if(keyBindings.containsKey(keyEvent.getCode().toString()) && currentAction.contains(keyBindings.get(keyEvent.getCode().toString()))) {
      currentAction.remove(keyBindings.get(keyEvent.getCode().toString()));
    }
  }
}
