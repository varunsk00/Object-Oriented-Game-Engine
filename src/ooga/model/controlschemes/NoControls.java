package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.model.actions.NoAction;

public class NoControls extends ControlScheme {

  public NoControls(Map<String, Action> controls) {
    super(controls);
  }

  @Override
  public List<Action> getCurrentAction() {
    return new ArrayList<>();
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {

  }

  @Override
  public void handleKeyReleased() {

  }
}