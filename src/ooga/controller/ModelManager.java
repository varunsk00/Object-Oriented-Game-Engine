package ooga.controller;

import ooga.model.CollisionEngine;
import ooga.model.PhysicsEngine;
import ooga.util.GameParser;

/**
 * This class handles the back end models needed by the controller
 */

public class ModelManager {

  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;

  /**
   * constructor for manager
   * @param gameParser : game parser
   */
  public ModelManager(GameParser gameParser) {

    physicsEngine = new PhysicsEngine(gameParser.parsePhysicsProfile());
    collisionEngine = new CollisionEngine();
  }

  /**
   * returns physics engine
   * @return physics engine
   */
  public PhysicsEngine getPhysicsEngine() {
    return physicsEngine;
  }

  /**
   * returns collision engine
   * @return coliision engine
   */
  public CollisionEngine getCollisionEngine() {
    return collisionEngine;
  }

  /**
   * applies physics logic
   * @param model
   */
  public void applyEntityPhysics(EntityWrapper model) {
    physicsEngine.applyForces(model.getModel());
  }

  /**
   * apply collision logic
   * @param subjectModel : current entity
   * @param targetModel : entity that is being collided with
   */
  public void produceCollisions(EntityWrapper subjectModel, EntityWrapper targetModel) {
    collisionEngine.produceCollisionActions(subjectModel.getModel(), targetModel.getModel());
  }

  /**
   * checks if health of entity is gone
   * @param entity : current entity
   * @return if health gone
   */
  public boolean checkHealthGone(EntityWrapper entity) {
    return entity.getModel().getHealth() <= 0;
  }
}
