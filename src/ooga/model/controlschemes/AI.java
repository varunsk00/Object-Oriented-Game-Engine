package ooga.model.controlschemes;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;

public class AI extends ControlScheme {
  private List<Action> possibleActions;

  public AI(List<Map<String, Action>> actions){
    super(actions);
    possibleActions = new ArrayList<>();
    for(Map<String, Action> map : actionMap){
      for(String s : map.keySet()){
        for(int i = 0; i < Integer.parseInt(s); i++){
          possibleActions.add(map.get(s));
        }
      }
    }
  }
  @Override
  public List<Action> getCurrentAction() {
    int index = (int)Math.floor(Math.random()*possibleActions.size());
    List<Action> currentActions = new ArrayList<>();
    currentActions.add(possibleActions.get(index));
    return currentActions;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {return;}
}
