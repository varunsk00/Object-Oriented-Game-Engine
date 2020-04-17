package ooga.model;

import java.util.Stack;
import javafx.scene.input.KeyEvent;
import ooga.controller.EntityWrapper;
import ooga.model.actions.AccelerateX;
import ooga.model.actions.AccelerateY;
import ooga.model.actions.Action;
import ooga.model.actions.VelocityY;
import ooga.model.controlschemes.ControlScheme;

public class PhysicsEngine {

  private double gravityForce = 20;
  private double dragForce = 1;
  private double frictionForce = 1;
  private EntityModel entityModel;

  public PhysicsEngine(String physicsProfile) {
    //TODO: Add a parser that parses through a physics file for the constants of friction
//    PhysicsParser physicsParser = new PhysicsParser(physicsProfile);
//    gravityForce = physicsParser.getDragForce();
//    dragForce = physicsParser.getDragForce();
//    frictionForce = physicsParser.getFrictionForce();
  }

  public void applyForces(EntityModel currentEntityModel){
    entityModel = currentEntityModel;
    applyResistiveForces();
    applyGravity();
  }

  //
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
    //System.out.print(entityModel.getEntityID() + ": ");
    if(entityModel.isOnGround()){
//      System.out.print("Gravity Negated");
      entityModel.setYVelocity(0);
    }
    else{
      //System.out.print("Gravity On");
      String gravityParameter = String.valueOf(gravityForce);
      entityModel.getActionStack().push(new AccelerateY(gravityParameter));
    }

    //System.out.println("--------------Distance: " + entityModel.getY());
  }
}
