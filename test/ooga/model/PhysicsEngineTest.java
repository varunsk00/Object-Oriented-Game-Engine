package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import ooga.util.GameParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhysicsEngineTest {
  private EntityWrapper myEntity;
  private PhysicsEngine myEngine;

  @BeforeEach
  void setUp() {
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
    GameParser gameParser = new GameParser("UnitTest", false);
    myEngine = new PhysicsEngine(gameParser.parsePhysicsProfile());

  }

  @Test
  void testFriction() {
    myEntity.getModel().setBoundedBelow(true);
    double xVel = 10;
    myEntity.getModel().setXVelocity(xVel);
    myEngine.applyForces(myEntity.getModel());
    myEntity.update(1000);
    assertTrue(myEntity.getModel().getXVelocity() < xVel);
  }

  @Test
  void testGravityFall(){
    myEntity.getModel().setBoundedBelow(false);
    double yVel = 10;
    myEntity.getModel().setYVelocity(yVel);
    myEngine.applyForces(myEntity.getModel());
    myEntity.update(1000);
    assertTrue(myEntity.getModel().getYVelocity() > yVel);
  }

  @Test
  void testGravityGrounded(){
    myEntity.getModel().setBoundedBelow(true);
    double yVel = -10;
    myEntity.getModel().setYVelocity(yVel);
    myEngine.applyForces(myEntity.getModel());
    myEntity.update(1000);
    assertTrue(myEntity.getModel().getYVelocity() == 0);
  }
}