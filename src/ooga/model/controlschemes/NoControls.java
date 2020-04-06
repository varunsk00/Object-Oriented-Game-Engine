package ooga.model.controlschemes;

import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.model.actions.NoAction;

public class NoControls extends ControlScheme {

  public NoControls(Map<String, Action> controls) {
    super(controls);
  }

  @Override
  public Action getCurrentAction() {
    return new NoAction();
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {

  }
}
