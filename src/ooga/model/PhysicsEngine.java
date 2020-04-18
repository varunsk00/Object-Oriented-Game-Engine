package ooga.model;

import java.util.Stack;
import javafx.scene.input.KeyEvent;
import ooga.controller.EntityWrapper;
import ooga.model.actions.AccelerateX;
import ooga.model.actions.AccelerateY;
import ooga.model.actions.Action;
import ooga.model.actions.VelocityY;
import ooga.model.controlschemes.ControlScheme;
import ooga.util.PhysicsProfile;

public class PhysicsEngine {

  private double gravityForce;
  private double dragForce;
  private double frictionForce;
  private EntityModel entityModel;

  public PhysicsEngine(PhysicsProfile physicsProfile) {
    gravityForce = physicsProfile.readGravity();
    dragForce = physicsProfile.readDrag();
    frictionForce = physicsProfile.readFriction();
  }

  public void applyForces(EntityModel currentEntityModel){
    entityModel = currentEntityModel;
    applyResistiveForces();
    applyGravity();
  }

  private void applyResistiveForces() {
    if (Math.abs(entityModel.getXVelocity()) > 0) {
      double opposingDirection = -Math.signum(entityModel.getXVelocity());
      if (entityModel.isOnGround()) {
        String frictionParameter = String.valueOf(opposingDirection * frictionForce);
        entityModel.getActionStack().push(new AccelerateX(frictionParameter));
      }
      else{
        String dragParameter = String.valueOf(opposingDirection * dragForce);
        entityModel.getActionStack().push(new AccelerateX(dragParameter));
      }
    }
  }

  private void applyGravity(){
    if(entityModel.isOnGround()){
      entityModel.setYVelocity(0);
    }
    else{
      String gravityParameter = String.valueOf(gravityForce);
      entityModel.getActionStack().push(new AccelerateY(gravityParameter));
    }

  }
}
