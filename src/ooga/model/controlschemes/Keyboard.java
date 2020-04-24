package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public class Keyboard extends ControlScheme {
  private Map<String, List<Action>> keyBindings;

  public Keyboard(List<ActionBundle> bindings){
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
  public void handleKeyInput(String key) {
    if(keyBindings.containsKey(key) && !currentAction.containsAll(keyBindings.get(key))) {
      currentAction.addAll(keyBindings.get(key));
//      System.out.println(key);
    }
  }

  @Override
  public void handleKeyReleased(String key) {
    if (keyBindings.containsKey(key) && currentAction
        .containsAll(keyBindings.get(key))) {
      currentAction.removeAll(keyBindings.get(key));
      //System.out.println(keyEvent.getCode().toString());
    }
  }
}
