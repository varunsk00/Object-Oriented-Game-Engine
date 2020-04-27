package ooga.apis.view;

/**
 * The internal API for the view. Defines how each class inside the
 * view hierarchy interacts with each other.
 */
public interface ViewInternalAPI {

  /**
   * Method in each entity, allowing for an updating of their position when called by the controller or
   * other entity
   * @param newx - the new X position to move to
   * @param newy - the new y position to move to
   * @param direction - UPDATED: THE DIRECTION THE ENTITY IS FACING
   */
  void update(double newx, double newy, boolean direction);
}
