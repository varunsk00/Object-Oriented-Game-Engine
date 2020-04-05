package ooga.apis.view;

/**
 * This interface represents the External API for the view section of the
 * project. These methods can be called by the model to change the state
 * and status of elements rendered on screen, such as moving their position,
 * removing it, updating something about its status, or adding a new entity.
 */
public interface ViewExternalAPI {

  /**
   * Model-called update entity method, which
   * specifies the entity and where its coordinates should take it.
   * Called by the model to update specific entities on the view side
   * @param id - the id of the entity to move
   * @param newx - the new x coordinate for the entity
   * @param newy - the new y coordinate for the entity
   */
  void updateEntityPosition(int id, double newx, double newy);

  /**
   * Removes an entity from rendering.
   * Called by model in cases such as a character dying, block being destroyed,
   * etc
   * @param id - the entity to be removed
   */
  void removeEntity(int id);

  /**
   * Adds an entity to rendering. Called
   * when a new entity is added to the game
   * (called in parallel to the model)
   */
  void addEntity();

  /**
   * Updates the status of a specific entity
   * with a new value. Used in cases such as
   * updating the score.
   * @param id - the entity to be updated
   * @param newValue - the new value for the entity
   */
  void updateEntity(int id, String newValue);
}
