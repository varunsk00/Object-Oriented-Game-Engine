package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollisionEngineTest {
  private EntityWrapper myEntity1;
  private EntityWrapper myEntity2;
  private CollisionEngine myEngine;

  @BeforeEach
  void setUp() {
    myEntity1 = new EntityWrapper("UnitTestEntity", null);
    myEntity2 = new EntityWrapper("UnitTestEntity", null);
    myEngine = new CollisionEngine();
  }

  @Test
  void testProduceCollisionActions() {
    double e1Y = myEntity1.getModel().getY();
    double e2Y = myEntity2.getModel().getY();
    myEngine.produceCollisionActions(myEntity1.getModel(), myEntity2.getModel());
    System.out.println(e1Y);
    System.out.println(myEntity1.getModel().getY());
    assertTrue(e1Y == myEntity1.getModel().getY()-10);
    assertTrue(e2Y == myEntity2.getModel().getY()-10);
  }
}