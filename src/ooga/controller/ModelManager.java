package ooga.controller;

import ooga.model.CollisionEngine;
import ooga.model.PhysicsEngine;
import ooga.util.GameParser;

public class ModelManager {

  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;

  public ModelManager(GameParser gameParser) {

    physicsEngine = new PhysicsEngine(gameParser.parsePhysicsProfile());
    collisionEngine = new CollisionEngine();
  }

  public void applyEntityPhysics(EntityWrapper model) {
    physicsEngine.applyForces(model.getModel());
  }

  public void produceCollisions(EntityWrapper subjectModel, EntityWrapper targetModel) {
    collisionEngine.produceCollisionActions(subjectModel.getModel(), targetModel.getModel());
  }

  public boolean checkHealthGone(EntityWrapper entity) {
    return entity.getModel().getHealth() <= 0;
  }
}
