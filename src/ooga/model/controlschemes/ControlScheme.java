package ooga.model.controlschemes;

import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public abstract class ControlScheme {
  protected Action currentAction;
  protected Map<String, Action> actionMap;

  public ControlScheme(Map<String, Action> controls){
    actionMap = controls;
  }

  public abstract Action getCurrentAction();

  public abstract void handleKeyInput(KeyEvent keyEvent);
}
