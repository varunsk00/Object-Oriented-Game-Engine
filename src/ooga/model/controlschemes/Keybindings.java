package ooga.model.controlschemes;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class Keybindings extends ControlScheme {
  private Map<String, Action> keyBinds;
  private Scene keyChecker;

  public Keybindings(){

  }

  public Keybindings(Map<String, Action> bindings){
    keyBinds = bindings;
  }

  @Override
  public Action getCurrentAction() {
    return currentAction;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {
    currentAction = keyBinds.get(keyEvent.getText());
  }
}
