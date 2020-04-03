package apis.model;

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
}
