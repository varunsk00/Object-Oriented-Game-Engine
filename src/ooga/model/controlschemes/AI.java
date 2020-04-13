package ooga.model.controlschemes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public class AI extends ControlScheme {
  private List<List<Action>> possibleActions;
  private List<Double> probabilities;
  double total;

  public AI(List<ActionBundle> actions){
    super(actions);
    possibleActions = new ArrayList<>();
    probabilities = new ArrayList<>();
    total = 0;
    for(ActionBundle bundle : actionMap) {
      total += Integer.parseInt(bundle.getId());
    }
    for(ActionBundle bundle : actionMap) {
      double probability = Double.parseDouble(bundle.getId())/total;
      probabilities.add(probability);
      possibleActions.add(bundle.getActions());
    }
  }
  @Override
  public List<Action> getCurrentAction() {
    double index = Math.random();
    double probSum = 0.0;
    for(int i = 0; i < probabilities.size(); i++){
      probSum += probabilities.get(i);
      if(index < probSum){
        return(possibleActions.get(i));
      }
    }
    return new ArrayList<>();
  }

  @Override
  public void handleKeyInput(String key) {return;}

  @Override
  public void handleKeyReleased(String key) {return;}
}
