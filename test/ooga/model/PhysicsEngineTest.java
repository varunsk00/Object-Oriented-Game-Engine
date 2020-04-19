package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import ooga.model.actions.Action;
import ooga.model.actions.VelocityX;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhysicsEngineTest {
  private EntityWrapper myEntity;
  private PhysicsEngine myEngine;

  @BeforeEach
  void setUp() {
    myEntity = new EntityWrapper("UnitTestEntity", null);
   // myEngine = new PhysicsEngine("yeet");
  }

  @Test
  void testFriction() {
    myEntity.getModel().setOnGround(true);
    double xVel = 10;
    myEntity.getModel().setXVelocity(xVel);
    myEngine.applyForces(myEntity.getModel());
    myEntity.update(1000);
    assertTrue(myEntity.getModel().getXVelocity() < xVel);
  }

  @Test
  void testGravityFall(){
    myEntity.getModel().setOnGround(false);
    double yVel = 10;
    myEntity.getModel().setYVelocity(yVel);
    myEngine.applyForces(myEntity.getModel());
    myEntity.update(1000);
    assertTrue(myEntity.getModel().getYVelocity() > yVel);
  }

  @Test
  void testGravityGrounded(){
    myEntity.getModel().setOnGround(true);
    double yVel = -10;
    myEntity.getModel().setYVelocity(yVel);
    myEngine.applyForces(myEntity.getModel());
    myEntity.update(1000);
    assertTrue(myEntity.getModel().getYVelocity() == 0);
  }
}