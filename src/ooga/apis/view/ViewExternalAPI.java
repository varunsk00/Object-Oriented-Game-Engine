package ooga.apis.view;

import javafx.scene.Node;
import javax.swing.text.html.parser.Entity;

/**
 * This interface represents the External API for the view section of the
 * project. These methods can be called by the model to change the state
 * and status of elements rendered on screen, such as moving their position,
 * removing it, updating something about its status, or adding a new entity.
 */
public interface ViewExternalAPI {


  /**
   * @deprecated: removeEntity takes in an EntityWrapper allowing us to
   * remove both the rendering and the object representing it.
   * Removes an entity from rendering.
   * Called by model in cases such as a character dying, block being destroyed,
   * etc
   * @param node - the entity to be removed
   */
  void removeEntity(Node node);

  /**
   * @deprecated: replaced by spawnEntity() in MainController class, which takes
   * in an EntityWrapper to be added instead of just a JFX node.
   * Adds an entity to rendering. Called
   * when a new entity is added to the game
   * (called in parallel to the model)
   */
  void addEntity(Node node);

}
