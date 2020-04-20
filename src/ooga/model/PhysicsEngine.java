package ooga.model;

import ooga.model.actions.AccelerateX;
import ooga.model.actions.AccelerateY;
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
    if (!entityModel.getFixed() || Math.abs(entityModel.getXVelocity()) > 0) {
      double opposingDirection = -Math.signum(entityModel.getXVelocity());
      if (entityModel.getBoundedBelow()) {
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

    //System.out.print(entityModel.getEntityID() + ": ");
    if(entityModel.getFixed() || entityModel.getBoundedBelow()){
      //System.out.print("Gravity Negated");
      entityModel.setYVelocity(0);
    }
    else{
      String gravityParameter = String.valueOf(gravityForce);
      entityModel.getActionStack().push(new AccelerateY(gravityParameter));
    }

  }
}
