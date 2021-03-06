package ooga.model.controlschemes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.util.ActionBundle;

public class Pattern extends ControlScheme {
  private int index;
  private int frameDuration;

  public Pattern(List<ActionBundle> actions){
    super(actions);
    index = 0;
    frameDuration = 0;
  }
  @Override
  public List<Action> getCurrentAction() {
    ActionBundle output = actionMap.get(index);
//    System.out.println(output);
//    System.out.println(output.getId());
//    System.out.println(frameDuration);
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
  public void handleKeyInput(String key) {return;}

  @Override
  public void handleKeyReleased(String key) {return;}
}
