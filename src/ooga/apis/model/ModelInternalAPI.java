package ooga.apis.model;

import ooga.model.actions.Action;

/**
 * Internal API for the model. Illustrates how different classes within the model communicate to each other.
 * Each method represents how information is processed and passed between classes in
 * the model.
 */
public interface ModelInternalAPI {

  /**
   * Called by an entity when seeing how it should act in the current
   * moment
   * @return an Action object specifying how the entity should act
   */
  Action getCurrentAction();
}
