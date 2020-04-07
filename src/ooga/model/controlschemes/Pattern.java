package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public class Pattern extends ControlScheme {
//  private List<List<Action>> patternList;
  private int index;
  private int frameDuration;

  public Pattern(List<ActionBundle> actions){
    super(actions);
//    patternList = new ArrayList<>();
//    for(ActionBundle bundle : actionMap) {
//      List<Action> tempList = new ArrayList<>(map.values());
//      for (String s : map.keySet()) {
//        for (int i = 0; i < Integer.parseInt(s); i++) {
//          patternList.add(tempList);
//        }
//        break;
//      }
//    }
    index = 0;
    frameDuration = 0;
  }
  @Override
  public List<Action> getCurrentAction() {
    ActionBundle output = actionMap.get(index);
    frameDuration++;

    if(frameDuration >= Integer.parseInt(output.getId())) {
      index++;
      if(index >= actionMap.size()){
        index = 0;
      }
      frameDuration = 0;
    }

    List<Action> currentActions = new ArrayList<>();
    currentActions.addAll(output.getActions());
    return currentActions;
  }

  @Override
  public void handleKeyInput(KeyEvent keyEvent) {return;}

  @Override
  public void handleKeyReleased(KeyEvent keyEvent) {return;}
}
