package apis.model;

import ooga.model.actions.Action;

public interface ModelInternalAPI {

  /**
   * Called by an entity when seeing how it should act in the current
   * moment
   * @return an Action object specifying how the entity should act
   */
  Action getCurrentAction();
}
