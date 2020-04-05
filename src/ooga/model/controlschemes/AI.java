package ooga.model.controlschemes;

import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class AI extends ControlScheme {
  private List<Action> possibleActions;

  public AI(){
  }

  public AI(List<Action> actions){
    possibleActions = actions;
  }
  @Override
  public Action getCurrentAction() {
    int index = (int)Math.floor(Math.random()*possibleActions.size());
    return possibleActions.get(index);
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}
}
