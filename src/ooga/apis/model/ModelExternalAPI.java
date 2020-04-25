package ooga.apis.model;

import javax.swing.text.html.parser.Entity;
import ooga.controller.EntityWrapper;

/**
 * External API for the model. Mediates how other components (view, controller)
 * communicate and send information to the model.
 */
public interface ModelExternalAPI {

  /**
   * Adds an entity to the model.
   * Called in parallel with view.
   * @deprecated: called in the controller class MainController rather than directly in the model side.
   */
  void addEntity();

  /**
   * Sets up the back end side of the game
   * @param gameSelect : game selected
   * @deprecated: set up in the view side in the StageManager, Game, and MainController classes
   */
  void setUpGameModel(String gameSelect);

  /**
   * Sends user data from the front end to back end
   * @deprecated: predicted method which was never used. Data is sent through specific methods rather than as a string.
   */
  String sendUserData();

  /**
   * Triggers the collisions on each of the entities
   * @param e - one of the Entities colliding
   * @param j - the other Entity colliding
   * @deprecated: replaced by the produceCollisions() method in class ModelManager
   */
  void collide(EntityWrapper e, EntityWrapper j);
}
