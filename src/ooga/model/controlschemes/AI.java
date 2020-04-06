package ooga.model.controlschemes;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class AI extends ControlScheme {
  private List<Action> possibleActions;

  public AI(Map<String, Action> actions){
    super(actions);
    possibleActions = new ArrayList<>();
    for(String s : actionMap.keySet()){
      for(int i = 0; i < Integer.parseInt(s); i++){
        possibleActions.add(actionMap.get(s));
      }
    }
  }
  @Override
  public Action getCurrentAction() {
    int index = (int)Math.floor(Math.random()*possibleActions.size());
    return possibleActions.get(index);
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}
}
