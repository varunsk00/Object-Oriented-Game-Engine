package apis.model;

import javax.swing.text.html.parser.Entity;

/**
 * External API for the model. Mediates how other components (view, controller)
 * communicate and send information to the model.
 */
public interface ModelExternalAPI {

  /**
   * Adds an entity to the model.
   * Called in parallel with view.
   */
  void addEntity();

  /**
   * Sets up the back end side of the game
   * @param gameSelect : game selected
   */
  void setUpGameModel(String gameSelect);

  /**
   * Sends user data from the front end to back end
   */
  String sendUserData();

  /**
   * Triggers the collisions on each of the entities
   * @param e - one of the Entities colliding
   * @param j - the other Entity colliding
   */
  void collide(Entity e, Entity j);
}
