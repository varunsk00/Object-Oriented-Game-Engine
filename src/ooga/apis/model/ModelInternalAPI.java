package ooga.apis.model;

import javax.swing.text.html.parser.Entity;
import ooga.model.actions.Action;

/**
 * Internal API for the model. Illustrates how different classes within the model communicate to each other.
 * Each method represents how information is processed and passed between classes in
 * the model.
 */
public interface ModelInternalAPI {

  /**
   * @deprecated: unnecessary action as most of the time we access
   * lists rather than single actions.
   * Called by an entity when seeing how it should act in the current
   * moment
   * @return an Action object specifying how the entity should act
   */
  Action getCurrentAction();

  /**
   * @deprecated: parameter has been changed to entitymodel
   * due to refactoring in the project
   * Called by an entity to execute an action:
   */
  void execute(Entity e);
}
