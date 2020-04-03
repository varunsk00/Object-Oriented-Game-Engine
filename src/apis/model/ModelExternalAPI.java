package apis.model;

/**
 * External API for the model. Handles the 
 */
public interface ModelExternalAPI {

  /**
   * Adds an entity to the model.
   * Called in parallel with view.
   */
  void addEntity();
}
