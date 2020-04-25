package ooga.controller;

import java.util.List;
import javax.swing.text.html.parser.Entity;
import ooga.apis.model.ModelExternalAPI;
import ooga.model.CollisionEngine;
import ooga.model.EntityModel;
import ooga.model.PhysicsEngine;
import ooga.util.GameParser;

/**
 * This class handles the back end models needed by the controller
 */
public class ModelManager implements ModelExternalAPI {
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
   * add entity to list
   */
  @Override
  public void addEntity() {
  }

  /**
   * sets up game model for selected game
   * @param gameSelect : game selected
   */
  @Override
  public void setUpGameModel(String gameSelect) {

  }

  /**
   * sends data
   * @return data
   */
  @Override
  public String sendUserData() {
    return null;
  }

  /**
   * checks collisions
   * @param e - one of the Entities colliding
   * @param j - the other Entity colliding
   */
  @Override
  public void collide(EntityWrapper e, EntityWrapper j) {

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
