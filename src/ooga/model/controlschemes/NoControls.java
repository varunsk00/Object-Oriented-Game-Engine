package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.model.actions.NoAction;
import ooga.util.ActionBundle;

public class NoControls extends ControlScheme {

  public NoControls(List<ActionBundle> controls) {
    super(controls);
  }

  @Override
  public List<Action> getCurrentAction() {
    return new ArrayList<>();
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {return;}
}
