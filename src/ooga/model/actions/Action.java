package ooga.model.actions;

import java.util.Collection;
import java.util.List;
import java.util.Stack;
import ooga.model.EntityModel;

public  interface Action {
  /**
   *
   *
   * @param entityModel entity model
   * @return
   */
  public Action execute(EntityModel entityModel);

  /**
   *
   */
  public double returnValue();

  /**
   * Returns a boolean for whether or not the screen's pen drawings should be cleared.
   *
   * @return a boolean, which should be false for all cases except ClearScreen or similar commands.
   */
  public static boolean toClear() {
    return false;
  }

  /**
   *
   *
   * @param action
   * @param entityModel
   * @param ret
   * @return
   */
  static double executeAndExtractValue(Action action, EntityModel entityModel, Stack<Action> ret) {
    ret.addAll((Collection<? extends Action>) action.execute(entityModel));
    return action.returnValue();
  }
}
