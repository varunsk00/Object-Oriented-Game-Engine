package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public class Keybindings extends ControlScheme {
  private Map<String, List<Action>> keyBindings;

  public Keybindings(List<ActionBundle> bindings){
    super(bindings);
    keyBindings = new HashMap<>();
    for(ActionBundle bundle : actionMap){
      keyBindings.put(bundle.getId(), bundle.getActions());
    }
    currentAction = new ArrayList<>();
  }

  @Override
  public List<Action> getCurrentAction() {
    return currentAction;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {
    if(keyBindings.containsKey(keyEvent.getCode().toString()) && !currentAction.containsAll(keyBindings.get(keyEvent.getCode().toString()))) {
      currentAction.addAll(keyBindings.get(keyEvent.getCode().toString()));
      //System.out.println(keyEvent.getCode().toString());
    }
  }

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {
    if (keyBindings.containsKey(keyEvent.getCode().toString()) && currentAction
        .containsAll(keyBindings.get(keyEvent.getCode().toString()))) {
      currentAction.removeAll(keyBindings.get(keyEvent.getCode().toString()));
      //System.out.println(keyEvent.getCode().toString());
    }
  }
}
