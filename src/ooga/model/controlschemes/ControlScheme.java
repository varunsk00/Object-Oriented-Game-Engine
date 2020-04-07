package ooga.model.controlschemes;

import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public abstract class ControlScheme {
  protected List<Action> currentAction;
  protected List<ActionBundle> actionMap;

  public ControlScheme(List<ActionBundle> controls){
    actionMap = controls;
  }

  public abstract List<Action> getCurrentAction();

  public abstract void handleKeyInput(KeyEvent keyEvent);

  public abstract void handleKeyReleased(KeyEvent keyEvent);
}
