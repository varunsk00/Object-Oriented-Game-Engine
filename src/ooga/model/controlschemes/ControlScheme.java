package ooga.model.controlschemes;

import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public abstract class ControlScheme {
  protected Action currentAction;

  public abstract Action getCurrentAction();

  public abstract void handleKeyInput(KeyEvent keyEvent);
}
