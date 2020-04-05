package ooga.model.controlschemes;

import java.util.Map;
import ooga.model.actions.Action;

public class Keybindings extends ControlScheme {
  private Map<String, Action> keyBinds;

  public Keybindings(){

  }

  public Keybindings(Map<String, Action> bindings){
    keyBinds = bindings;
  }

  private void setCurrentInput(KeyboardEventHandler e){
    currentAction = keyBinds.get(e.getCharacter());
  }

  @Override
  public Action getCurrentAction() {
    return null;
  }
}
