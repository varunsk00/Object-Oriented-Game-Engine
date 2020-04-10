package ooga.model.controlschemes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public class AI extends ControlScheme {
  private Map<Double, List<Action>> possibleActions;

  public AI(List<ActionBundle> actions){
    super(actions);
    possibleActions = new HashMap<>();
    double total = 0;
    for(ActionBundle bundle : actionMap) {
      total += Integer.parseInt(bundle.getId());
    }
    for(ActionBundle bundle : actionMap) {
      double probability = Double.parseDouble(bundle.getId())/total;
      possibleActions.put(probability, bundle.getActions());
    }
  }
  @Override
  public List<Action> getCurrentAction() {
    double index = Math.random();
    double probSum = 0.0;
    for(Double prob : possibleActions.keySet()){
      probSum += prob;
      if(index < probSum){
        return(possibleActions.get(prob));
      }
    }
    return new ArrayList<>();
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {return;}
}
