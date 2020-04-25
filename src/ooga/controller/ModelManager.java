package ooga.controller;

import java.util.List;
import javax.swing.text.html.parser.Entity;
import ooga.apis.model.ModelExternalAPI;
import ooga.model.CollisionEngine;
import ooga.model.EntityModel;
import ooga.model.PhysicsEngine;
import ooga.util.GameParser;

public class ModelManager implements ModelExternalAPI {
  private PhysicsEngine physicsEngine;
  private CollisionEngine collisionEngine;

  public ModelManager(GameParser gameParser) {

    physicsEngine = new PhysicsEngine(gameParser.parsePhysicsProfile());
    collisionEngine = new CollisionEngine();
  }

  @Override
  public void addEntity() {

  }

  @Override
  public void setUpGameModel(String gameSelect) {

  }

  @Override
  public String sendUserData() {
    return null;
  }

  @Override
  public void collide(EntityWrapper e, EntityWrapper j) {

  }

  public PhysicsEngine getPhysicsEngine() {
    return physicsEngine;
  }

  public CollisionEngine getCollisionEngine() {
    return collisionEngine;
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
