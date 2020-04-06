package ooga.model;

import java.util.Stack;
import javafx.scene.input.KeyEvent;
import ooga.controller.EntityWrapper;
import ooga.model.actions.AccelerateX;
import ooga.model.actions.Action;
import ooga.model.controlschemes.ControlScheme;

public class PhysicsEngine {

  private double gravityForce = 10;
  private double dragForce = 1;
  private double frictionForce = 1;
  private EntityModel entityModel;

  public PhysicsEngine(String physicsProfile) {
//    PhysicsParser physicsParser = new PhysicsParser(physicsProfile);
//    gravityForce = physicsParser.getDragForce();
//    dragForce = physicsParser.getDragForce();
//    frictionForce = physicsParser.getFrictionForce();
  }

  public void applyForces(EntityModel currentEntityModel){
    entityModel = currentEntityModel;
    applyFriction();
  }

  private void applyFriction(){
    if(entityModel.isOnGround() && Math.abs(entityModel.getXVelocity()) > 0){
      double frictionDirection = -Math.signum(entityModel.getXVelocity());
      String frictionParameter = String.valueOf(frictionDirection * frictionForce);
      entityModel.getActionStack().push(new AccelerateX(frictionParameter));
    }
  }
}
