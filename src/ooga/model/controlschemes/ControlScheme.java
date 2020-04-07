package ooga.model.controlschemes;

import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public abstract class ControlScheme {
  protected List<Action> currentAction;
  protected Map<String, Action> actionMap;

  public ControlScheme(Map<String, Action> controls){
    actionMap = controls;
  }

  public abstract List<Action> getCurrentAction();

  public abstract void handleKeyInput(KeyEvent keyEvent);

  public abstract void handleKeyReleased(KeyEvent keyEvent);
}
